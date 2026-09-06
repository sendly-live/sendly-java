package com.sendly.resources;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sendly.Sendly;
import com.sendly.TestHelpers;
import com.sendly.exceptions.*;
import com.sendly.models.CreateRcsAgentRequest;
import com.sendly.models.IdempotentRequestOptions;
import com.sendly.models.RcsAgentBasics;
import com.sendly.models.RcsAgentDetails;
import com.sendly.models.RcsAgentEmailContact;
import com.sendly.models.RcsAgentPhoneContact;
import com.sendly.models.RcsAgentResponse;
import com.sendly.models.RcsAgentUseCase;
import com.sendly.models.RcsAgentWebsiteContact;
import com.sendly.models.RcsAgentsResponse;
import com.sendly.models.RcsBrand;
import com.sendly.models.RcsBrandAddress;
import com.sendly.models.RcsBrandContact;
import com.sendly.models.RcsBrandInput;
import com.sendly.models.RcsBrandResponse;
import com.sendly.models.RcsCampaign;
import com.sendly.models.RcsConsentSettings;
import com.sendly.models.RcsCustomerStage;
import com.sendly.models.RcsDossier;
import com.sendly.models.RcsErrorCode;
import com.sendly.models.RcsInteraction;
import com.sendly.models.RcsInteractionType;
import com.sendly.models.RcsLaunchRequest;
import com.sendly.models.RcsLegalEntityType;
import com.sendly.models.RcsOptInMethod;
import com.sendly.models.RcsOptInMethodType;
import com.sendly.models.RcsOrganizationType;
import com.sendly.models.RcsRegistration;
import com.sendly.models.RcsReviewStatus;
import com.sendly.models.RcsTestDevice;
import com.sendly.models.RcsTestDeviceInput;
import com.sendly.models.RcsTestDevicesResponse;
import com.sendly.models.RcsTesting;
import com.sendly.models.UpdateRcsAgentRequest;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the RCS registration operations — registration, dossier, brands,
 * agents, test devices, submit and launch.
 */
class RcsRegistrationTest {
    private static final String NOT_ENABLED_BODY =
        "{\"error\":\"rcs_not_enabled\",\"message\":\"RCS registration isn't enabled for this account yet.\"}";

    private static final String BRAND_JSON =
        "{\"id\":\"rbr_1\",\"reviewStatus\":\"draft\",\"customerStage\":\"draft\",\"displayName\":\"Acme\"," +
        "\"legalName\":\"Acme Holdings LLC\",\"legalEntityType\":\"LIMITED_LIABILITY_COMPANY\"," +
        "\"organizationType\":\"PRIVATE_PROFIT\",\"stockSymbol\":null,\"websiteUrl\":\"https://acme.example\"," +
        "\"ein\":\"12-3456789\",\"address\":{\"line1\":\"1 Main St\",\"line2\":null,\"city\":\"Chicago\"," +
        "\"state\":\"IL\",\"postalCode\":\"60601\",\"countryCode\":\"US\"}," +
        "\"contact\":{\"firstName\":\"Sam\",\"lastName\":\"Lee\",\"title\":null,\"email\":\"sam@acme.example\"," +
        "\"phoneNumber\":\"+13125550100\"},\"reviewNote\":null,\"rejectionReason\":null," +
        "\"submittedForReviewAt\":null,\"sentToCarrierAt\":null,\"verifiedAt\":null," +
        "\"createdAt\":\"2026-09-01T10:00:00.000Z\",\"updatedAt\":\"2026-09-01T10:00:00.000Z\"}";

    private static final String DEVICE_JSON =
        "{\"id\":\"rtd_1\",\"phoneNumber\":\"+13125550100\",\"label\":\"Sam's phone\",\"inviteStatus\":\"PENDING\"," +
        "\"createdAt\":\"2026-09-01T10:00:00.000Z\"}";

    private static final String AGENT_JSON =
        "{\"id\":\"rag_1\",\"brandId\":\"rbr_1\",\"status\":\"draft\",\"reviewStatus\":\"draft\"," +
        "\"customerStage\":\"draft\",\"displayName\":\"Acme\",\"useCase\":\"TRANSACTIONAL\",\"hostingRegion\":null," +
        "\"basics\":{\"displayName\":\"Acme\",\"useCase\":\"TRANSACTIONAL\",\"hostingRegion\":null," +
        "\"description\":\"Order updates\",\"logoUrl\":\"https://acme.example/logo.png\"," +
        "\"heroUrl\":\"https://acme.example/hero.png\",\"brandColor\":\"#0055FF\"," +
        "\"privacyPolicyUrl\":\"https://acme.example/privacy\",\"termsAndConditionsUrl\":\"https://acme.example/terms\"," +
        "\"phoneNumber\":{\"number\":\"+13125550100\",\"label\":\"Support\"}," +
        "\"website\":{\"url\":\"https://acme.example\",\"label\":\"Visit us\"}," +
        "\"email\":{\"address\":\"help@acme.example\",\"label\":\"Email us\"}}," +
        "\"campaign\":{\"companyOverview\":\"Acme sells widgets\",\"agentOverview\":\"Order updates\"," +
        "\"additionalInformation\":null,\"interactions\":[{\"interactionType\":\"TRANSACTIONAL_UPDATES\"," +
        "\"description\":\"Order status\"}],\"messageExamples\":[\"Shipped!\",\"Out for delivery\",\"Delivered\"]," +
        "\"consentSettings\":{\"optInMethods\":[{\"methodType\":\"WEBSITE\",\"description\":\"Checkout\"}]," +
        "\"callToAction\":\"Text me updates\",\"callToActionUrl\":\"https://acme.example/checkout\"," +
        "\"callToActionMediaUrl\":null,\"doubleOptIn\":false,\"doubleOptInMessage\":null," +
        "\"optInMessage\":\"You're in\",\"helpResponse\":\"Email us\",\"optOutResponse\":\"Bye\"}}," +
        "\"testing\":{\"testUrl\":\"https://acme.example/test\",\"messageId\":null,\"additionalInformation\":null}," +
        "\"reviewNote\":null,\"rejectionReason\":null,\"testDevices\":[" + DEVICE_JSON + "]," +
        "\"submittedForReviewAt\":null,\"basicsSubmittedAt\":null,\"launchSubmittedAt\":null,\"liveAt\":null," +
        "\"createdAt\":\"2026-09-01T10:00:00.000Z\",\"updatedAt\":\"2026-09-01T10:00:00.000Z\"}";

    private MockWebServer mockServer;
    private Sendly client;

    @BeforeEach
    void setUp() throws IOException {
        mockServer = new MockWebServer();
        mockServer.start();

        Sendly.Builder builder = new Sendly.Builder()
                .baseUrl(mockServer.url("/").toString())
                .maxRetries(0);

        client = new Sendly("sk_live_123", builder);
    }

    @AfterEach
    void tearDown() throws IOException {
        mockServer.shutdown();
    }

    private static MockResponse notEnabled() {
        return new MockResponse()
                .setResponseCode(404)
                .setBody(NOT_ENABLED_BODY)
                .addHeader("Content-Type", "application/json");
    }

    private static MockResponse created(String body) {
        return TestHelpers.mockSuccess(body).setResponseCode(201);
    }

    private static JsonObject bodyOf(RecordedRequest request) {
        return JsonParser.parseString(request.getBody().readUtf8()).getAsJsonObject();
    }

    private static void assertNotEnabled(NotFoundException e) {
        assertEquals("RCS registration isn't enabled for this account yet.", e.getMessage());
        assertEquals(RcsErrorCode.NOT_ENABLED, e.getApiErrorCode());
        assertEquals(404, e.getStatusCode());
        assertTrue(e.getFieldErrors().isEmpty());
    }

    // ==================== registration().get() Tests ====================

    @Test
    void testRegistrationGet_happyPath() throws Exception {
        mockServer.enqueue(TestHelpers.mockSuccess(
            "{\"brand\":" + BRAND_JSON + ",\"agent\":" + AGENT_JSON + ",\"devices\":[" + DEVICE_JSON + "]," +
            "\"stage\":\"draft\",\"usEligible\":true}"
        ));

        RcsRegistration registration = client.rcs().registration().get();

        assertEquals("rbr_1", registration.getBrand().getId());
        assertEquals("rag_1", registration.getAgent().getId());
        assertEquals(1, registration.getDevices().size());
        assertEquals("+13125550100", registration.getDevices().get(0).getPhoneNumber());
        assertEquals(RcsCustomerStage.DRAFT, registration.getStage());
        assertTrue(registration.isUsEligible());

        RecordedRequest request = mockServer.takeRequest();
        assertEquals("GET", request.getMethod());
        assertTrue(request.getPath().endsWith("/rcs/registration"));
        assertNull(request.getHeader("Idempotency-Key"));
    }

    @Test
    void testRegistrationGet_nothingYet_nullsAndEmptyDevices() throws Exception {
        mockServer.enqueue(TestHelpers.mockSuccess(
            "{\"brand\":null,\"agent\":null,\"devices\":[],\"stage\":\"draft\",\"usEligible\":false}"
        ));

        RcsRegistration registration = client.rcs().registration().get();

        assertNull(registration.getBrand());
        assertNull(registration.getAgent());
        assertTrue(registration.getDevices().isEmpty());
        assertEquals("draft", registration.getStage());
        assertFalse(registration.isUsEligible());
    }

    @Test
    void testRegistrationGet_404NotEnabled_throwsNotFoundException() {
        mockServer.enqueue(notEnabled());

        NotFoundException e = assertThrows(NotFoundException.class, () -> client.rcs().registration().get());
        assertNotEnabled(e);
    }

    // ==================== dossier().get() Tests ====================

    @Test
    void testDossierGet_happyPath_prefillFeedsBrandCreate() throws Exception {
        mockServer.enqueue(TestHelpers.mockSuccess(
            "{\"brand\":{\"legalName\":\"Acme Holdings LLC\",\"displayName\":\"Acme\",\"ein\":\"123456789\"," +
            "\"organizationType\":\"PRIVATE_PROFIT\",\"websiteUrl\":\"https://acme.example\"," +
            "\"address\":{\"line1\":\"1 Main St\",\"city\":\"Chicago\",\"state\":\"IL\",\"postalCode\":\"60601\",\"countryCode\":\"US\"}," +
            "\"contact\":{\"firstName\":\"Sam\",\"lastName\":\"Lee\",\"email\":\"sam@acme.example\",\"phoneNumber\":\"+13125550100\"}}," +
            "\"usEligible\":true,\"source\":\"tendlc\"}"
        ));

        RcsDossier dossier = client.rcs().dossier().get();

        assertEquals(RcsDossier.SOURCE_TENDLC, dossier.getSource());
        assertTrue(dossier.isUsEligible());
        RcsBrandInput brand = dossier.getBrand();
        assertEquals("Acme Holdings LLC", brand.getLegalName());
        assertEquals("Acme", brand.getDisplayName());
        assertEquals("123456789", brand.getEin());
        assertEquals(RcsOrganizationType.PRIVATE_PROFIT, brand.getOrganizationType());
        assertEquals("Chicago", brand.getAddress().getCity());
        assertEquals("US", brand.getAddress().getCountryCode());
        assertEquals("Sam", brand.getContact().getFirstName());
        assertNull(brand.getLegalEntityType());

        JsonObject roundTrip = brand.toBuilder()
                .legalEntityType(RcsLegalEntityType.LIMITED_LIABILITY_COMPANY)
                .build()
                .toJson();
        assertEquals("Acme Holdings LLC", roundTrip.get("legalName").getAsString());
        assertEquals("LIMITED_LIABILITY_COMPANY", roundTrip.get("legalEntityType").getAsString());
        assertEquals("US", roundTrip.getAsJsonObject("address").get("countryCode").getAsString());
        assertFalse(roundTrip.has("stockSymbol"));

        RecordedRequest request = mockServer.takeRequest();
        assertEquals("GET", request.getMethod());
        assertTrue(request.getPath().endsWith("/rcs/dossier"));
    }

    @Test
    void testDossierGet_none_emptyBrand() throws Exception {
        mockServer.enqueue(TestHelpers.mockSuccess("{\"brand\":{},\"usEligible\":true,\"source\":\"none\"}"));

        RcsDossier dossier = client.rcs().dossier().get();

        assertEquals(RcsDossier.SOURCE_NONE, dossier.getSource());
        assertNotNull(dossier.getBrand());
        assertNull(dossier.getBrand().getLegalName());
        assertNull(dossier.getBrand().getAddress());
        assertEquals(0, dossier.getBrand().toJson().size());
    }

    @Test
    void testDossierGet_404NotEnabled_throwsNotFoundException() {
        mockServer.enqueue(notEnabled());

        NotFoundException e = assertThrows(NotFoundException.class, () -> client.rcs().dossier().get());
        assertNotEnabled(e);
    }

    // ==================== brands().create() Tests ====================

    @Test
    void testBrandsCreate_happyPath() throws Exception {
        mockServer.enqueue(created("{\"brand\":" + BRAND_JSON + "}"));

        RcsBrandResponse response = client.rcs().brands().create(RcsBrandInput.builder()
                .displayName("Acme")
                .legalName("Acme Holdings LLC")
                .legalEntityType(RcsLegalEntityType.LIMITED_LIABILITY_COMPANY)
                .organizationType(RcsOrganizationType.PRIVATE_PROFIT)
                .websiteUrl("https://acme.example")
                .ein("12-3456789")
                .address(RcsBrandAddress.builder()
                        .line1("1 Main St").city("Chicago").state("IL").postalCode("60601").countryCode("US")
                        .build())
                .contact(RcsBrandContact.builder()
                        .firstName("Sam").lastName("Lee").email("sam@acme.example").phoneNumber("+13125550100")
                        .build())
                .build());

        RcsBrand brand = response.getBrand();
        assertEquals("rbr_1", brand.getId());
        assertEquals(RcsReviewStatus.DRAFT, brand.getReviewStatus());
        assertEquals(RcsCustomerStage.DRAFT, brand.getCustomerStage());
        assertEquals("Acme", brand.getDisplayName());
        assertEquals("Acme Holdings LLC", brand.getLegalName());
        assertEquals("LIMITED_LIABILITY_COMPANY", brand.getLegalEntityType());
        assertEquals("PRIVATE_PROFIT", brand.getOrganizationType());
        assertNull(brand.getStockSymbol());
        assertEquals("https://acme.example", brand.getWebsiteUrl());
        assertEquals("12-3456789", brand.getEin());
        assertEquals("1 Main St", brand.getAddress().getLine1());
        assertNull(brand.getAddress().getLine2());
        assertEquals("US", brand.getAddress().getCountryCode());
        assertEquals("Lee", brand.getContact().getLastName());
        assertNull(brand.getContact().getTitle());
        assertEquals("+13125550100", brand.getContact().getPhoneNumber());
        assertNull(brand.getReviewNote());
        assertNull(brand.getRejectionReason());
        assertNull(brand.getSubmittedForReviewAt());
        assertNull(brand.getSentToCarrierAt());
        assertNull(brand.getVerifiedAt());
        assertEquals("2026-09-01T10:00:00.000Z", brand.getCreatedAt());
        assertEquals("2026-09-01T10:00:00.000Z", brand.getUpdatedAt());

        RecordedRequest request = mockServer.takeRequest();
        assertEquals("POST", request.getMethod());
        assertTrue(request.getPath().endsWith("/rcs/brands"));
        assertNotNull(request.getHeader("Idempotency-Key"));
        assertTrue(request.getHeader("Idempotency-Key").startsWith("sendly-java-retry-"));
        JsonObject body = bodyOf(request);
        assertEquals("Acme", body.get("displayName").getAsString());
        assertEquals("Acme Holdings LLC", body.get("legalName").getAsString());
        assertEquals("LIMITED_LIABILITY_COMPANY", body.get("legalEntityType").getAsString());
        assertEquals("PRIVATE_PROFIT", body.get("organizationType").getAsString());
        assertEquals("https://acme.example", body.get("websiteUrl").getAsString());
        assertEquals("12-3456789", body.get("ein").getAsString());
        assertFalse(body.has("stockSymbol"));
        assertFalse(body.has("profileId"));
        JsonObject address = body.getAsJsonObject("address");
        assertEquals("1 Main St", address.get("line1").getAsString());
        assertFalse(address.has("line2"));
        assertEquals("US", address.get("countryCode").getAsString());
        JsonObject contact = body.getAsJsonObject("contact");
        assertEquals("sam@acme.example", contact.get("email").getAsString());
        assertFalse(contact.has("title"));
    }

    @Test
    void testBrandsCreate_callerIdempotencyKey_isSent() throws Exception {
        mockServer.enqueue(created("{\"brand\":" + BRAND_JSON + "}"));

        client.rcs().brands().create(RcsBrandInput.builder().displayName("Acme").build(),
                new IdempotentRequestOptions("brand-create-1"));

        RecordedRequest request = mockServer.takeRequest();
        assertEquals("brand-create-1", request.getHeader("Idempotency-Key"));
    }

    @Test
    void testBrandsCreate_nullInput_throwsValidationException() {
        assertThrows(ValidationException.class, () -> client.rcs().brands().create(null));
    }

    @Test
    void testBrandsCreate_422UsOnly_surfacesApiErrorCode() {
        mockServer.enqueue(new MockResponse().setResponseCode(422)
                .setBody("{\"error\":\"rcs_us_only\",\"message\":\"RCS registration is available to US businesses for now.\"}")
                .addHeader("Content-Type", "application/json"));

        ValidationException e = assertThrows(ValidationException.class, () ->
            client.rcs().brands().create(RcsBrandInput.builder()
                    .address(RcsBrandAddress.builder().countryCode("GB").build())
                    .build()));
        assertEquals(RcsErrorCode.US_ONLY, e.getApiErrorCode());
        assertEquals("RCS registration is available to US businesses for now.", e.getMessage());
    }

    @Test
    void testBrandsCreate_404NotEnabled_throwsNotFoundException() {
        mockServer.enqueue(notEnabled());

        NotFoundException e = assertThrows(NotFoundException.class, () ->
            client.rcs().brands().create(RcsBrandInput.builder().displayName("Acme").build()));
        assertNotEnabled(e);
    }

    // ==================== brands().update() Tests ====================

    @Test
    void testBrandsUpdate_happyPath_patchesOnlySetFields() throws Exception {
        mockServer.enqueue(TestHelpers.mockSuccess("{\"brand\":" + BRAND_JSON + "}"));

        RcsBrandResponse response = client.rcs().brands().update("rbr_1", RcsBrandInput.builder()
                .websiteUrl("https://acme.example")
                .contact(RcsBrandContact.builder().title("COO").build())
                .build());

        assertEquals("rbr_1", response.getBrand().getId());

        RecordedRequest request = mockServer.takeRequest();
        assertEquals("PATCH", request.getMethod());
        assertTrue(request.getPath().endsWith("/rcs/brands/rbr_1"));
        assertNull(request.getHeader("Idempotency-Key"));
        JsonObject body = bodyOf(request);
        assertEquals(2, body.size());
        assertEquals("https://acme.example", body.get("websiteUrl").getAsString());
        assertEquals("COO", body.getAsJsonObject("contact").get("title").getAsString());
        assertEquals(1, body.getAsJsonObject("contact").size());
    }

    @Test
    void testBrandsUpdate_callerIdempotencyKey_isSentOnPatch() throws Exception {
        mockServer.enqueue(TestHelpers.mockSuccess("{\"brand\":" + BRAND_JSON + "}"));

        client.rcs().brands().update("rbr_1", RcsBrandInput.builder().displayName("Acme").build(),
                new IdempotentRequestOptions("brand-update-1"));

        RecordedRequest request = mockServer.takeRequest();
        assertEquals("PATCH", request.getMethod());
        assertEquals("brand-update-1", request.getHeader("Idempotency-Key"));
    }

    @Test
    void testBrandsUpdate_encodesId() throws Exception {
        mockServer.enqueue(TestHelpers.mockSuccess("{\"brand\":" + BRAND_JSON + "}"));

        client.rcs().brands().update("rbr/1 x", RcsBrandInput.builder().displayName("Acme").build());

        RecordedRequest request = mockServer.takeRequest();
        assertTrue(request.getPath().endsWith("/rcs/brands/rbr%2F1+x"));
    }

    @Test
    void testBrandsUpdate_missingArgs_throwsValidationException() {
        assertThrows(ValidationException.class, () ->
            client.rcs().brands().update("", RcsBrandInput.builder().build()));
        assertThrows(ValidationException.class, () ->
            client.rcs().brands().update("rbr_1", null));
    }

    @Test
    void testBrandsUpdate_409FieldLocked_surfacesApiErrorCode() {
        mockServer.enqueue(new MockResponse().setResponseCode(409)
                .setBody("{\"error\":\"rcs_field_locked\",\"message\":\"This registration is being reviewed; we will email you if changes are needed.\"}")
                .addHeader("Content-Type", "application/json"));

        SendlyException e = assertThrows(SendlyException.class, () ->
            client.rcs().brands().update("rbr_1", RcsBrandInput.builder().displayName("Acme").build()));
        assertEquals(409, e.getStatusCode());
        assertEquals(RcsErrorCode.FIELD_LOCKED, e.getApiErrorCode());
    }

    @Test
    void testBrandsUpdate_404NotEnabled_throwsNotFoundException() {
        mockServer.enqueue(notEnabled());

        NotFoundException e = assertThrows(NotFoundException.class, () ->
            client.rcs().brands().update("rbr_1", RcsBrandInput.builder().displayName("Acme").build()));
        assertNotEnabled(e);
    }

    // ==================== agents().create() Tests ====================

    @Test
    void testAgentsCreate_happyPath() throws Exception {
        mockServer.enqueue(created("{\"agent\":" + AGENT_JSON + "}"));

        RcsAgentResponse response = client.rcs().agents().create(CreateRcsAgentRequest.builder()
                .brandId("rbr_1")
                .displayName("Acme")
                .useCase(RcsAgentUseCase.TRANSACTIONAL)
                .basics(RcsAgentBasics.builder()
                        .description("Order updates")
                        .logoUrl("https://acme.example/logo.png")
                        .heroUrl("https://acme.example/hero.png")
                        .brandColor("#0055FF")
                        .privacyPolicyUrl("https://acme.example/privacy")
                        .termsAndConditionsUrl("https://acme.example/terms")
                        .phoneNumber(new RcsAgentPhoneContact("+13125550100", "Support"))
                        .website(new RcsAgentWebsiteContact("https://acme.example", "Visit us"))
                        .email(new RcsAgentEmailContact("help@acme.example", "Email us"))
                        .build())
                .build());

        RcsAgentDetails agent = response.getAgent();
        assertEquals("rag_1", agent.getId());
        assertEquals("rbr_1", agent.getBrandId());
        assertEquals("draft", agent.getStatus());
        assertEquals(RcsReviewStatus.DRAFT, agent.getReviewStatus());
        assertEquals(RcsCustomerStage.DRAFT, agent.getCustomerStage());
        assertEquals("Acme", agent.getDisplayName());
        assertEquals("TRANSACTIONAL", agent.getUseCase());
        assertNull(agent.getHostingRegion());
        assertEquals("Order updates", agent.getBasics().getDescription());
        assertEquals("https://acme.example/logo.png", agent.getBasics().getLogoUrl());
        assertEquals("https://acme.example/hero.png", agent.getBasics().getHeroUrl());
        assertEquals("#0055FF", agent.getBasics().getBrandColor());
        assertEquals("+13125550100", agent.getBasics().getPhoneNumber().getNumber());
        assertEquals("Support", agent.getBasics().getPhoneNumber().getLabel());
        assertEquals("https://acme.example", agent.getBasics().getWebsite().getUrl());
        assertEquals("help@acme.example", agent.getBasics().getEmail().getAddress());
        assertEquals("Acme sells widgets", agent.getCampaign().getCompanyOverview());
        assertEquals(1, agent.getCampaign().getInteractions().size());
        assertEquals("TRANSACTIONAL_UPDATES", agent.getCampaign().getInteractions().get(0).getInteractionType());
        assertEquals(3, agent.getCampaign().getMessageExamples().size());
        assertEquals("WEBSITE", agent.getCampaign().getConsentSettings().getOptInMethods().get(0).getMethodType());
        assertEquals(Boolean.FALSE, agent.getCampaign().getConsentSettings().getDoubleOptIn());
        assertNull(agent.getCampaign().getConsentSettings().getCallToActionMediaUrl());
        assertEquals("https://acme.example/test", agent.getTesting().getTestUrl());
        assertNull(agent.getTesting().getMessageId());
        assertEquals(1, agent.getTestDevices().size());
        assertEquals("rtd_1", agent.getTestDevices().get(0).getId());
        assertEquals("PENDING", agent.getTestDevices().get(0).getInviteStatus());
        assertNull(agent.getSubmittedForReviewAt());
        assertNull(agent.getLiveAt());

        assertEquals(1, response.getDevices().size());
        assertEquals(RcsCustomerStage.DRAFT, response.getStage());

        RecordedRequest request = mockServer.takeRequest();
        assertEquals("POST", request.getMethod());
        assertTrue(request.getPath().endsWith("/rcs/agents"));
        assertTrue(request.getHeader("Idempotency-Key").startsWith("sendly-java-retry-"));
        JsonObject body = bodyOf(request);
        assertEquals("rbr_1", body.get("brandId").getAsString());
        assertEquals("Acme", body.get("displayName").getAsString());
        assertEquals("TRANSACTIONAL", body.get("useCase").getAsString());
        assertFalse(body.has("campaign"));
        assertFalse(body.has("testing"));
        JsonObject basics = body.getAsJsonObject("basics");
        assertEquals("Order updates", basics.get("description").getAsString());
        assertEquals("https://acme.example/logo.png", basics.get("logoUrl").getAsString());
        assertEquals("#0055FF", basics.get("brandColor").getAsString());
        assertFalse(basics.has("hostingRegion"));
        assertFalse(basics.has("profileId"));
        assertEquals("+13125550100", basics.getAsJsonObject("phoneNumber").get("number").getAsString());
        assertEquals("Visit us", basics.getAsJsonObject("website").get("label").getAsString());
        assertEquals("help@acme.example", basics.getAsJsonObject("email").get("address").getAsString());
    }

    @Test
    void testAgentsCreate_missingBrandId_throwsValidationException() {
        assertThrows(ValidationException.class, () -> client.rcs().agents().create(null));
        assertThrows(ValidationException.class, () ->
            client.rcs().agents().create(CreateRcsAgentRequest.builder().displayName("Acme").build()));
    }

    @Test
    void testAgentsCreate_422NonHttpsMedia_surfacesFieldErrors() {
        mockServer.enqueue(new MockResponse().setResponseCode(422)
                .setBody("{\"error\":\"rcs_invalid_content\"," +
                        "\"message\":\"Assets can't be uploaded over the API. Logo, hero, and call-to-action media must be public https:// URLs.\"," +
                        "\"errors\":[{\"path\":\"basics.logoUrl\",\"message\":\"Must be a public https:// URL\"}]}")
                .addHeader("Content-Type", "application/json"));

        ValidationException e = assertThrows(ValidationException.class, () ->
            client.rcs().agents().create(CreateRcsAgentRequest.builder()
                    .brandId("rbr_1")
                    .basics(RcsAgentBasics.builder().logoUrl("http://acme.example/logo.png").build())
                    .build()));
        assertEquals(RcsErrorCode.INVALID_CONTENT, e.getApiErrorCode());
        assertEquals(1, e.getFieldErrors().size());
        assertEquals("basics.logoUrl", e.getFieldErrors().get(0).getPath());
        assertEquals("Must be a public https:// URL", e.getFieldErrors().get(0).getMessage());
    }

    @Test
    void testAgentsCreate_404NotEnabled_throwsNotFoundException() {
        mockServer.enqueue(notEnabled());

        NotFoundException e = assertThrows(NotFoundException.class, () ->
            client.rcs().agents().create(CreateRcsAgentRequest.builder().brandId("rbr_1").build()));
        assertNotEnabled(e);
    }

    // ==================== agents().get() Tests ====================

    @Test
    void testAgentsGet_happyPath() throws Exception {
        mockServer.enqueue(TestHelpers.mockSuccess(
            "{\"agent\":" + AGENT_JSON + ",\"devices\":[" + DEVICE_JSON + "],\"stage\":\"draft\"}"
        ));

        RcsAgentResponse response = client.rcs().agents().get("rag_1");

        assertEquals("rag_1", response.getAgent().getId());
        assertEquals(1, response.getDevices().size());
        assertEquals("Sam's phone", response.getDevices().get(0).getLabel());
        assertEquals("draft", response.getStage());

        RecordedRequest request = mockServer.takeRequest();
        assertEquals("GET", request.getMethod());
        assertTrue(request.getPath().endsWith("/rcs/agents/rag_1"));
    }

    @Test
    void testAgentsGet_missingId_throwsValidationException() {
        assertThrows(ValidationException.class, () -> client.rcs().agents().get(null));
        assertThrows(ValidationException.class, () -> client.rcs().agents().get(""));
    }

    @Test
    void testAgentsGet_404NotFound_surfacesApiErrorCode() {
        mockServer.enqueue(new MockResponse().setResponseCode(404)
                .setBody("{\"error\":\"rcs_not_found\",\"message\":\"Agent not found\"}")
                .addHeader("Content-Type", "application/json"));

        NotFoundException e = assertThrows(NotFoundException.class, () -> client.rcs().agents().get("rag_missing"));
        assertEquals(RcsErrorCode.NOT_FOUND, e.getApiErrorCode());
    }

    @Test
    void testAgentsGet_404NotEnabled_throwsNotFoundException() {
        mockServer.enqueue(notEnabled());

        NotFoundException e = assertThrows(NotFoundException.class, () -> client.rcs().agents().get("rag_1"));
        assertNotEnabled(e);
    }

    // ==================== agents().update() Tests ====================

    @Test
    void testAgentsUpdate_happyPath_campaignAndTesting() throws Exception {
        mockServer.enqueue(TestHelpers.mockSuccess("{\"agent\":" + AGENT_JSON + "}"));

        RcsAgentResponse response = client.rcs().agents().update("rag_1", UpdateRcsAgentRequest.builder()
                .campaign(RcsCampaign.builder()
                        .agentOverview("Order updates")
                        .interactions(List.of(new RcsInteraction(RcsInteractionType.TRANSACTIONAL_UPDATES, "Order status")))
                        .messageExamples(List.of("Shipped!", "Out for delivery", "Delivered"))
                        .consentSettings(RcsConsentSettings.builder()
                                .optInMethods(List.of(new RcsOptInMethod(RcsOptInMethodType.WEBSITE, "Checkout")))
                                .callToAction("Text me updates")
                                .callToActionUrl("https://acme.example/checkout")
                                .doubleOptIn(false)
                                .optInMessage("You're in")
                                .helpResponse("Email us")
                                .optOutResponse("Bye")
                                .build())
                        .build())
                .testing(RcsTesting.builder().testUrl("https://acme.example/test").build())
                .build());

        assertEquals("rag_1", response.getAgent().getId());
        assertEquals(1, response.getDevices().size());
        assertEquals("draft", response.getStage());

        RecordedRequest request = mockServer.takeRequest();
        assertEquals("PATCH", request.getMethod());
        assertTrue(request.getPath().endsWith("/rcs/agents/rag_1"));
        assertNull(request.getHeader("Idempotency-Key"));
        JsonObject body = bodyOf(request);
        assertFalse(body.has("displayName"));
        assertFalse(body.has("basics"));
        JsonObject campaign = body.getAsJsonObject("campaign");
        assertEquals("Order updates", campaign.get("agentOverview").getAsString());
        assertFalse(campaign.has("companyOverview"));
        assertEquals(1, campaign.getAsJsonArray("interactions").size());
        assertEquals("TRANSACTIONAL_UPDATES",
            campaign.getAsJsonArray("interactions").get(0).getAsJsonObject().get("interactionType").getAsString());
        assertEquals(3, campaign.getAsJsonArray("messageExamples").size());
        JsonObject consent = campaign.getAsJsonObject("consentSettings");
        assertEquals("WEBSITE", consent.getAsJsonArray("optInMethods").get(0).getAsJsonObject().get("methodType").getAsString());
        assertFalse(consent.get("doubleOptIn").getAsBoolean());
        assertEquals("Bye", consent.get("optOutResponse").getAsString());
        assertFalse(consent.has("callToActionMediaUrl"));
        assertEquals("https://acme.example/test", body.getAsJsonObject("testing").get("testUrl").getAsString());
    }

    @Test
    void testAgentsUpdate_basicsOnly_withCallerKey() throws Exception {
        mockServer.enqueue(TestHelpers.mockSuccess("{\"agent\":" + AGENT_JSON + "}"));

        client.rcs().agents().update("rag_1", UpdateRcsAgentRequest.builder()
                .displayName("Acme Inc")
                .useCase(RcsAgentUseCase.MULTI_USE)
                .basics(RcsAgentBasics.builder().brandColor("#FFF").build())
                .build(), new IdempotentRequestOptions("agent-update-1"));

        RecordedRequest request = mockServer.takeRequest();
        assertEquals("agent-update-1", request.getHeader("Idempotency-Key"));
        JsonObject body = bodyOf(request);
        assertEquals("Acme Inc", body.get("displayName").getAsString());
        assertEquals("MULTI_USE", body.get("useCase").getAsString());
        assertEquals("#FFF", body.getAsJsonObject("basics").get("brandColor").getAsString());
        assertFalse(body.has("campaign"));
        assertFalse(body.has("testing"));
    }

    @Test
    void testAgentsUpdate_missingArgs_throwsValidationException() {
        assertThrows(ValidationException.class, () ->
            client.rcs().agents().update(null, UpdateRcsAgentRequest.builder().build()));
        assertThrows(ValidationException.class, () ->
            client.rcs().agents().update("rag_1", null));
    }

    @Test
    void testAgentsUpdate_404NotEnabled_throwsNotFoundException() {
        mockServer.enqueue(notEnabled());

        NotFoundException e = assertThrows(NotFoundException.class, () ->
            client.rcs().agents().update("rag_1", UpdateRcsAgentRequest.builder().displayName("Acme").build()));
        assertNotEnabled(e);
    }

    // ==================== agents().setTestDevices() Tests ====================

    @Test
    void testSetTestDevices_happyPath() throws Exception {
        mockServer.enqueue(TestHelpers.mockSuccess(
            "{\"devices\":[" + DEVICE_JSON + ",{\"id\":\"rtd_2\",\"phoneNumber\":\"+13125550101\",\"label\":null," +
            "\"inviteStatus\":null,\"createdAt\":\"2026-09-01T10:05:00.000Z\"}]}"
        ));

        RcsTestDevicesResponse response = client.rcs().agents().setTestDevices("rag_1", List.of(
                new RcsTestDeviceInput("+13125550100", "Sam's phone"),
                new RcsTestDeviceInput("(312) 555-0101")));

        assertEquals(2, response.getDevices().size());
        RcsTestDevice second = response.getDevices().get(1);
        assertEquals("rtd_2", second.getId());
        assertEquals("+13125550101", second.getPhoneNumber());
        assertNull(second.getLabel());
        assertNull(second.getInviteStatus());
        assertEquals("2026-09-01T10:05:00.000Z", second.getCreatedAt());

        RecordedRequest request = mockServer.takeRequest();
        assertEquals("PUT", request.getMethod());
        assertTrue(request.getPath().endsWith("/rcs/agents/rag_1/test-devices"));
        assertNull(request.getHeader("Idempotency-Key"));
        JsonObject body = bodyOf(request);
        assertEquals(2, body.getAsJsonArray("devices").size());
        JsonObject first = body.getAsJsonArray("devices").get(0).getAsJsonObject();
        assertEquals("+13125550100", first.get("phoneNumber").getAsString());
        assertEquals("Sam's phone", first.get("label").getAsString());
        JsonObject secondSent = body.getAsJsonArray("devices").get(1).getAsJsonObject();
        assertEquals("(312) 555-0101", secondSent.get("phoneNumber").getAsString());
        assertFalse(secondSent.has("label"));
    }

    @Test
    void testSetTestDevices_emptyList_sendsEmptyArray() throws Exception {
        mockServer.enqueue(TestHelpers.mockSuccess("{\"devices\":[]}"));

        RcsTestDevicesResponse response = client.rcs().agents().setTestDevices("rag_1", List.of());

        assertTrue(response.getDevices().isEmpty());
        JsonObject body = bodyOf(mockServer.takeRequest());
        assertEquals(0, body.getAsJsonArray("devices").size());
    }

    @Test
    void testSetTestDevices_callerIdempotencyKey_isSentOnPut() throws Exception {
        mockServer.enqueue(TestHelpers.mockSuccess("{\"devices\":[]}"));

        client.rcs().agents().setTestDevices("rag_1", List.of(new RcsTestDeviceInput("+13125550100")),
                new IdempotentRequestOptions("devices-1"));

        RecordedRequest request = mockServer.takeRequest();
        assertEquals("PUT", request.getMethod());
        assertEquals("devices-1", request.getHeader("Idempotency-Key"));
    }

    @Test
    void testSetTestDevices_localValidation() {
        assertThrows(ValidationException.class, () -> client.rcs().agents().setTestDevices("", List.of()));
        assertThrows(ValidationException.class, () -> client.rcs().agents().setTestDevices("rag_1", null));
        assertThrows(ValidationException.class, () ->
            client.rcs().agents().setTestDevices("rag_1", List.of(new RcsTestDeviceInput(""))));

        List<RcsTestDeviceInput> tooMany = new ArrayList<>();
        for (int i = 0; i < 21; i++) {
            tooMany.add(new RcsTestDeviceInput("+1312555" + String.format("%04d", i)));
        }
        assertThrows(ValidationException.class, () -> client.rcs().agents().setTestDevices("rag_1", tooMany));
        assertEquals(0, mockServer.getRequestCount());
    }

    @Test
    void testSetTestDevices_422BadNumber_surfacesFieldErrors() {
        mockServer.enqueue(new MockResponse().setResponseCode(422)
                .setBody("{\"error\":\"rcs_invalid_content\",\"message\":\"Check the device list\"," +
                        "\"errors\":[{\"path\":\"devices.0.phoneNumber\"," +
                        "\"message\":\"Enter the device's phone number in E.164 format, like +13125550100\"}]}")
                .addHeader("Content-Type", "application/json"));

        ValidationException e = assertThrows(ValidationException.class, () ->
            client.rcs().agents().setTestDevices("rag_1", List.of(new RcsTestDeviceInput("nope"))));
        assertEquals(RcsErrorCode.INVALID_CONTENT, e.getApiErrorCode());
        assertEquals("devices.0.phoneNumber", e.getFieldErrors().get(0).getPath());
    }

    @Test
    void testSetTestDevices_404NotEnabled_throwsNotFoundException() {
        mockServer.enqueue(notEnabled());

        NotFoundException e = assertThrows(NotFoundException.class, () ->
            client.rcs().agents().setTestDevices("rag_1", List.of(new RcsTestDeviceInput("+13125550100"))));
        assertNotEnabled(e);
    }

    // ==================== agents().submit() Tests ====================

    @Test
    void testSubmit_happyPath() throws Exception {
        String submitted = AGENT_JSON
                .replace("\"reviewStatus\":\"draft\"", "\"reviewStatus\":\"awaiting_review\"")
                .replace("\"customerStage\":\"draft\"", "\"customerStage\":\"in_review\"")
                .replace("\"submittedForReviewAt\":null", "\"submittedForReviewAt\":\"2026-09-02T10:00:00.000Z\"");
        mockServer.enqueue(TestHelpers.mockSuccess("{\"agent\":" + submitted + ",\"stage\":\"in_review\"}"));

        RcsAgentResponse response = client.rcs().agents().submit("rag_1");

        assertEquals(RcsReviewStatus.AWAITING_REVIEW, response.getAgent().getReviewStatus());
        assertEquals(RcsCustomerStage.IN_REVIEW, response.getAgent().getCustomerStage());
        assertEquals("2026-09-02T10:00:00.000Z", response.getAgent().getSubmittedForReviewAt());
        assertEquals(RcsCustomerStage.IN_REVIEW, response.getStage());

        RecordedRequest request = mockServer.takeRequest();
        assertEquals("POST", request.getMethod());
        assertTrue(request.getPath().endsWith("/rcs/agents/rag_1/submit"));
        assertTrue(request.getHeader("Idempotency-Key").startsWith("sendly-java-retry-"));
        assertEquals(0, bodyOf(request).size());
    }

    @Test
    void testSubmit_callerIdempotencyKey_isSent() throws Exception {
        mockServer.enqueue(TestHelpers.mockSuccess("{\"agent\":" + AGENT_JSON + ",\"stage\":\"in_review\"}"));

        client.rcs().agents().submit("rag_1", new IdempotentRequestOptions("submit-rag_1"));

        assertEquals("submit-rag_1", mockServer.takeRequest().getHeader("Idempotency-Key"));
    }

    @Test
    void testSubmit_missingId_throwsValidationException() {
        assertThrows(ValidationException.class, () -> client.rcs().agents().submit(null));
    }

    @Test
    void testSubmit_422Incomplete_surfacesFieldErrors() {
        mockServer.enqueue(new MockResponse().setResponseCode(422)
                .setBody("{\"error\":\"rcs_invalid_content\",\"message\":\"Finish the brand and agent first\"," +
                        "\"errors\":[{\"path\":\"brand.ein\",\"message\":\"Enter a 9-digit EIN\"}," +
                        "{\"path\":\"agent.logoUrl\",\"message\":\"Must be a public https:// URL\"}]}")
                .addHeader("Content-Type", "application/json"));

        ValidationException e = assertThrows(ValidationException.class, () -> client.rcs().agents().submit("rag_1"));
        assertEquals(RcsErrorCode.INVALID_CONTENT, e.getApiErrorCode());
        assertEquals(2, e.getFieldErrors().size());
        assertEquals("brand.ein", e.getFieldErrors().get(0).getPath());
        assertEquals("Enter a 9-digit EIN", e.getFieldErrors().get(0).getMessage());
        assertEquals("agent.logoUrl", e.getFieldErrors().get(1).getPath());
    }

    @Test
    void testSubmit_409BrandNotVerified_surfacesApiErrorCode() {
        mockServer.enqueue(new MockResponse().setResponseCode(409)
                .setBody("{\"error\":\"rcs_brand_not_verified\",\"message\":\"The brand failed verification\"}")
                .addHeader("Content-Type", "application/json"));

        SendlyException e = assertThrows(SendlyException.class, () -> client.rcs().agents().submit("rag_1"));
        assertEquals(409, e.getStatusCode());
        assertEquals(RcsErrorCode.BRAND_NOT_VERIFIED, e.getApiErrorCode());
    }

    @Test
    void testSubmit_404NotEnabled_throwsNotFoundException() {
        mockServer.enqueue(notEnabled());

        NotFoundException e = assertThrows(NotFoundException.class, () -> client.rcs().agents().submit("rag_1"));
        assertNotEnabled(e);
    }

    // ==================== agents().requestLaunch() Tests ====================

    @Test
    void testRequestLaunch_happyPath_noBody() throws Exception {
        String launching = AGENT_JSON
                .replace("\"reviewStatus\":\"draft\"", "\"reviewStatus\":\"launch_requested\"")
                .replace("\"customerStage\":\"draft\"", "\"customerStage\":\"launch_review\"");
        mockServer.enqueue(TestHelpers.mockSuccess("{\"agent\":" + launching + ",\"stage\":\"launch_review\"}"));

        RcsAgentResponse response = client.rcs().agents().requestLaunch("rag_1");

        assertEquals(RcsReviewStatus.LAUNCH_REQUESTED, response.getAgent().getReviewStatus());
        assertEquals(RcsCustomerStage.LAUNCH_REVIEW, response.getStage());

        RecordedRequest request = mockServer.takeRequest();
        assertEquals("POST", request.getMethod());
        assertTrue(request.getPath().endsWith("/rcs/agents/rag_1/request-launch"));
        assertTrue(request.getHeader("Idempotency-Key").startsWith("sendly-java-retry-"));
        assertEquals(0, bodyOf(request).size());
    }

    @Test
    void testRequestLaunch_withTestingDetails_andCallerKey() throws Exception {
        mockServer.enqueue(TestHelpers.mockSuccess("{\"agent\":" + AGENT_JSON + ",\"stage\":\"launch_review\"}"));

        client.rcs().agents().requestLaunch("rag_1", RcsLaunchRequest.builder()
                .testUrl("https://acme.example/test")
                .testingAdditionalInformation("Tap the card to see the flow")
                .build(), new IdempotentRequestOptions("launch-rag_1"));

        RecordedRequest request = mockServer.takeRequest();
        assertEquals("launch-rag_1", request.getHeader("Idempotency-Key"));
        JsonObject body = bodyOf(request);
        assertEquals("https://acme.example/test", body.get("testUrl").getAsString());
        assertEquals("Tap the card to see the flow", body.get("testingAdditionalInformation").getAsString());
    }

    @Test
    void testRequestLaunch_missingId_throwsValidationException() {
        assertThrows(ValidationException.class, () -> client.rcs().agents().requestLaunch(""));
    }

    @Test
    void testRequestLaunch_409NotReady_surfacesApiErrorCode() {
        mockServer.enqueue(new MockResponse().setResponseCode(409)
                .setBody("{\"error\":\"rcs_launch_not_ready\",\"message\":\"This agent isn't ready to launch yet. Finish testing on an invited device first.\"}")
                .addHeader("Content-Type", "application/json"));

        SendlyException e = assertThrows(SendlyException.class, () -> client.rcs().agents().requestLaunch("rag_1"));
        assertEquals(409, e.getStatusCode());
        assertEquals(RcsErrorCode.LAUNCH_NOT_READY, e.getApiErrorCode());
        assertEquals("This agent isn't ready to launch yet. Finish testing on an invited device first.", e.getMessage());
    }

    @Test
    void testRequestLaunch_404NotEnabled_throwsNotFoundException() {
        mockServer.enqueue(notEnabled());

        NotFoundException e = assertThrows(NotFoundException.class, () -> client.rcs().agents().requestLaunch("rag_1"));
        assertNotEnabled(e);
    }

    // ==================== agents().list() stage (additive) ====================

    @Test
    void testAgentsList_readsStage() throws Exception {
        mockServer.enqueue(TestHelpers.mockSuccess(
            "{\"agents\":[{\"id\":\"rag_1\",\"name\":\"Acme\",\"status\":\"testing\",\"useCase\":\"OTP\"," +
            "\"sendable\":true,\"stage\":\"testing\",\"createdAt\":\"2026-07-30T10:00:00.000Z\"}]}"
        ));

        RcsAgentsResponse response = client.rcs().agents().list();

        assertEquals(RcsCustomerStage.TESTING, response.getAgents().get(0).getStage());
        assertEquals("testing", response.getAgents().get(0).getStatus());
    }

    // ==================== Scope / permission errors ====================

    @Test
    void testInsufficientScope_403_surfacesApiErrorCode() {
        mockServer.enqueue(new MockResponse().setResponseCode(403)
                .setBody("{\"error\":\"insufficient_permissions\",\"message\":\"This API key lacks the rcs:write scope\"}")
                .addHeader("Content-Type", "application/json"));

        SendlyException e = assertThrows(SendlyException.class, () ->
            client.rcs().brands().create(RcsBrandInput.builder().displayName("Acme").build()));
        assertEquals(403, e.getStatusCode());
        assertEquals(RcsErrorCode.INSUFFICIENT_PERMISSIONS, e.getApiErrorCode());
    }
}
