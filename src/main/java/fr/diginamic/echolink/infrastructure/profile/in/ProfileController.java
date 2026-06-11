package fr.diginamic.echolink.infrastructure.profile.in;

import fr.diginamic.echolink.application.profile.port.in.ProfileDeleteUseCase;
import fr.diginamic.echolink.application.profile.port.in.ProfileGetUseCase;
import fr.diginamic.echolink.application.profile.port.in.ProfileUpdateUseCase;
import fr.diginamic.echolink.domain.profile.Profile;
import fr.diginamic.echolink.domain.profile.ProfileUpdateRequest;
import fr.diginamic.echolink.domain.profile.exception.ProfileNotAllowedException;
import fr.diginamic.echolink.domain.profile.exception.ProfileNotFoundException;
import fr.diginamic.echolink.infrastructure.common.in.dto.ErrorMessageQuery;
import fr.diginamic.echolink.infrastructure.common.in.dto.MessageResponse;
import fr.diginamic.echolink.infrastructure.profile.in.dto.ProfileQuery;
import fr.diginamic.echolink.infrastructure.profile.in.mapper.ProfileQueryMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * REST controller exposing profile management endpoints.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/profile", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Profile", description = "Profile management")
public class ProfileController {

    /**
     * Use case responsible for retrieving profiles.
     */
    private final ProfileGetUseCase getUseCase;

    /**
     * Use case responsible for updating profiles.
     */
    private final ProfileUpdateUseCase updateUseCase;

    /**
     * Use case responsible for deleting profiles.
     */
    private final ProfileDeleteUseCase deleteUseCase;

    /**
     * Mapper used to convert profile domain objects into query DTOs.
     */
    private final ProfileQueryMapper mapper;

    /**
     * Retrieves the authenticated profile.
     *
     * @param authentication current authentication information
     * @return authenticated profile information
     * @throws ProfileNotFoundException if the authenticated profile cannot be found
     */
    @GetMapping("/me")
    @RolesAllowed({"ADMIN", "USER"})
    @Operation(
            operationId = "getMyProfile",
            summary = "Get authenticated profile",
            description = "Returns the profile linked to the current JWT",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Profile retrieved successfully",
                            content = @Content(schema = @Schema(implementation = ProfileQuery.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Profile not found",
                            content = @Content(schema = @Schema(implementation = ErrorMessageQuery.class))
                    )
            }
    )
    public ResponseEntity<ProfileQuery> me(Authentication authentication) throws ProfileNotFoundException {

        if (authentication == null || authentication.getPrincipal() == null) {
            return ResponseEntity.status(401).build();
        }

        Jwt jwt = (Jwt) authentication.getPrincipal();
        Profile profile = getUseCase.getById(UUID.fromString(jwt.getSubject()));
        ProfileQuery query = mapper.toQuery(profile);

        return ResponseEntity.ok(query);
    }

    /**
     * Retrieves a profile by its unique identifier.
     *
     * @param profileId unique identifier of the profile
     * @return profile information
     * @throws ProfileNotFoundException if no profile is found with the specified identifier
     */
    @GetMapping("/{profileId}")
    @RolesAllowed({"ADMIN", "USER"})
    @Operation(
            operationId = "getProfileById",
            summary = "Get profile by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Profile found",
                            content = @Content(schema = @Schema(implementation = ProfileQuery.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Profile not found",
                            content = @Content(schema = @Schema(implementation = ErrorMessageQuery.class))
                    )
            }
    )
    public ResponseEntity<ProfileQuery> getProfileById(
            @Parameter(description = "Profile UUID", required = true) @PathVariable UUID profileId
    ) throws ProfileNotFoundException {

        Profile profile = getUseCase.getById(profileId);
        ProfileQuery query = mapper.toQuery(profile);

        return ResponseEntity.ok(query);
    }

    /**
     * Retrieves all available profiles.
     *
     * @return list of profile information
     */
    @GetMapping("/all")
    @RolesAllowed({"ADMIN"})
    @Operation(
            operationId = "getAllProfiles",
            summary = "Get all profiles",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Profiles retrieved",
                            content = @Content(
                                    array = @ArraySchema(schema = @Schema(implementation = ProfileQuery.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(schema = @Schema(hidden = true))
                    )
            }
    )
    public ResponseEntity<List<ProfileQuery>> getAllProfiles() {

        List<Profile> profiles = getUseCase.getAllProfiles();
        List<ProfileQuery> query = profiles.stream().map(mapper::toQuery).toList();

        return ResponseEntity.ok(query);
    }

    /**
     * Updates a profile.
     *
     * @param profileId unique identifier of the profile to update
     * @param request request containing updated profile information
     * @return updated profile information
     * @throws ProfileNotFoundException if no profile is found with the specified identifier
     */
    @PutMapping("/{profileId}")
    @RolesAllowed({"ADMIN", "USER"})
    @Operation(
            operationId = "updateProfile",
            summary = "Update profile",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Profile updated",
                            content = @Content(schema = @Schema(implementation = ProfileQuery.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request",
                            content = @Content(schema = @Schema(implementation = ErrorMessageQuery.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(schema = @Schema(implementation = ErrorMessageQuery.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Profile not found",
                            content = @Content(schema = @Schema(implementation = ErrorMessageQuery.class))
                    )
            }
    )
    public ResponseEntity<ProfileQuery> updateProfile(
            @Parameter(description = "Profile UUID", required = true) @PathVariable UUID profileId,
            @Valid @RequestBody ProfileUpdateRequest request,
            Authentication authentication
    ) throws ProfileNotFoundException, ProfileNotAllowedException {

        Jwt jwt = (Jwt) authentication.getPrincipal();
        UUID id = UUID.fromString(Objects.requireNonNull(jwt).getSubject());
        Profile user = getUseCase.getById(id);

        Profile profile = updateUseCase.update(user, profileId, request);
        ProfileQuery query = mapper.toQuery(profile);

        return ResponseEntity.ok(query);
    }

    /**
     * Deletes a profile.
     *
     * @param profileId unique identifier of the profile to delete
     * @return confirmation message
     * @throws ProfileNotFoundException if no profile is found with the specified identifier
     */
    @DeleteMapping("/{profileId}")
    @RolesAllowed({"ADMIN"})
    @Operation(
            operationId = "deleteProfile",
            summary = "Delete profile",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Profile deleted",
                            content = @Content(schema = @Schema(implementation = MessageResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Profile not found",
                            content = @Content(schema = @Schema(implementation = ErrorMessageQuery.class))
                    )
            }
    )
    public ResponseEntity<MessageResponse> deleteProfile(
            @Parameter(description = "Profile UUID", required = true) @PathVariable UUID profileId
    ) throws ProfileNotFoundException {

        deleteUseCase.delete(profileId);

        return ResponseEntity.ok(new MessageResponse("Profile with id: " + profileId + " is correctly deleted"));
    }
}
