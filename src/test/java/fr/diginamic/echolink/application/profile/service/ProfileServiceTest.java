package fr.diginamic.echolink.application.profile.service;

import fr.diginamic.echolink.application.profile.port.out.ProfileRepository;
import fr.diginamic.echolink.domain.profile.Profile;
import fr.diginamic.echolink.domain.profile.ProfileRole;
import fr.diginamic.echolink.domain.profile.ProfileUpdateRequest;
import fr.diginamic.echolink.domain.profile.exception.ProfileNotAllowedException;
import fr.diginamic.echolink.domain.profile.exception.ProfileNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static fr.diginamic.echolink.domain.profile.ProfileTestData.givenProfile1;
import static fr.diginamic.echolink.domain.profile.ProfileTestData.givenProfile2;
import static fr.diginamic.echolink.domain.profile.ProfileTestData.givenProfile3;
import static fr.diginamic.echolink.domain.profile.ProfileTestData.givenProfileUpdateRequest;
import static fr.diginamic.echolink.domain.profile.ProfileTestData.givenProfileUpdateRequestWithNullPassword;
import static fr.diginamic.echolink.domain.profile.ProfileTestData.givenProfileUpdateRequestWithoutEmail;
import static fr.diginamic.echolink.domain.profile.ProfileTestData.givenProfileUpdateRequestWithoutPassword;
import static fr.diginamic.echolink.domain.shared.SharedTestData.givenUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {

    @Mock
    private ProfileRepository repository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private ProfileService service;

    @Test
    void should_return_profile_by_id() throws ProfileNotFoundException {
        // GIVEN
        UUID id = givenUUID();
        Profile profile = givenProfile1();

        when(repository.getById(id))
                .thenReturn(Optional.of(profile));

        // WHEN
        Profile result = service.getById(id);

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(profile.getId());
        assertThat(result.getFirstName()).isEqualTo(profile.getFirstName());
        assertThat(result.getLastName()).isEqualTo(profile.getLastName());
        assertThat(result.getEmail()).isEqualTo(profile.getEmail());

        verify(repository).getById(id);
    }

    @Test
    void should_throw_exception_when_profile_not_found() {
        // GIVEN
        UUID id = givenUUID();

        when(repository.getById(id))
                .thenReturn(Optional.empty());

        // WHEN / THEN
        assertThatThrownBy(() -> service.getById(id))
                .isInstanceOf(ProfileNotFoundException.class)
                .hasMessage("Profile with id " + id + " not found");

        verify(repository).getById(id);
    }

    @Test
    void should_return_all_profiles() {
        // GIVEN
        List<Profile> profiles = List.of(
                givenProfile1(),
                givenProfile2(),
                givenProfile3()
        );

        when(repository.getAllProfiles())
                .thenReturn(profiles);

        // WHEN
        List<Profile> result = service.getAllProfiles();

        // THEN
        assertThat(result)
                .hasSize(3)
                .extracting(Profile::getId)
                .containsExactly(
                        profiles.get(0).getId(),
                        profiles.get(1).getId(),
                        profiles.get(2).getId()
                );

        verify(repository).getAllProfiles();
    }

    @Test
    void should_update_profile() throws ProfileNotFoundException, ProfileNotAllowedException {
        // GIVEN
        Profile profile = givenProfile1();

        ProfileUpdateRequest request = givenProfileUpdateRequest();

        when(repository.getById(profile.getId()))
                .thenReturn(Optional.of(profile));

        when(passwordEncoder.encode(request.password()))
                .thenReturn("encodedPassword");

        when(repository.save(profile))
                .thenReturn(profile);

        // WHEN
        Profile result = service.update(profile, profile.getId(), request);

        // THEN
        assertThat(result.getFirstName()).isEqualTo(request.firstName());
        assertThat(result.getLastName()).isEqualTo(request.lastName());
        assertThat(result.getPseudo()).isEqualTo(request.pseudo());
        assertThat(result.getEmail()).isEqualTo(request.email());
        assertThat(result.getPassword()).isEqualTo("encodedPassword");
        assertThat(result.getCity()).isEqualTo(request.city());
        assertThat(result.getPostalCode()).isEqualTo(request.postalCode());
        assertThat(result.getAddress()).isEqualTo(request.address());
        assertThat(result.getPhoneNumber()).isEqualTo(request.phoneNumber());
        assertThat(result.getLinkImgProfile()).isEqualTo(request.linkImgProfile());

        verify(passwordEncoder).encode(request.password());
        verify(repository).save(profile);
    }

    @Test
    void should_keep_existing_email_when_request_email_is_null()
            throws ProfileNotFoundException, ProfileNotAllowedException {
        // GIVEN
        Profile profile = givenProfile1();
        String initialEmail = profile.getEmail();

        ProfileUpdateRequest request = givenProfileUpdateRequestWithoutEmail();

        when(repository.getById(profile.getId()))
                .thenReturn(Optional.of(profile));

        when(passwordEncoder.encode(request.password()))
                .thenReturn("newEncodedPassword");

        when(repository.save(profile))
                .thenReturn(profile);

        // WHEN
        Profile result = service.update(profile, profile.getId(), request);

        // THEN
        assertThat(profile.getEmail()).isEqualTo(initialEmail);

        assertThat(result.getFirstName()).isEqualTo(request.firstName());
        assertThat(result.getLastName()).isEqualTo(request.lastName());
        assertThat(result.getPseudo()).isEqualTo(request.pseudo());
        assertThat(result.getPassword()).isEqualTo("newEncodedPassword");
        assertThat(result.getCity()).isEqualTo(request.city());
        assertThat(result.getPostalCode()).isEqualTo(request.postalCode());
        assertThat(result.getAddress()).isEqualTo(request.address());
        assertThat(result.getPhoneNumber()).isEqualTo(request.phoneNumber());
        assertThat(result.getLinkImgProfile()).isEqualTo(request.linkImgProfile());

        verify(passwordEncoder).encode(request.password());
        verify(repository).save(profile);
    }

    @Test
    void should_not_encode_password_when_password_is_empty()
            throws ProfileNotFoundException, ProfileNotAllowedException {
        // GIVEN
        Profile profile = givenProfile1();
        String originalPassword = profile.getPassword();

        ProfileUpdateRequest request = givenProfileUpdateRequestWithoutPassword();

        when(repository.getById(profile.getId()))
                .thenReturn(Optional.of(profile));

        when(repository.save(profile))
                .thenReturn(profile);

        // WHEN
        Profile result = service.update(profile, profile.getId(), request);

        // THEN
        assertThat(profile.getPassword()).isEqualTo(originalPassword);

        assertThat(result.getFirstName()).isEqualTo(request.firstName());
        assertThat(result.getLastName()).isEqualTo(request.lastName());
        assertThat(result.getPseudo()).isEqualTo(request.pseudo());
        assertThat(result.getEmail()).isEqualTo(request.email());
        assertThat(result.getCity()).isEqualTo(request.city());
        assertThat(result.getPostalCode()).isEqualTo(request.postalCode());
        assertThat(result.getAddress()).isEqualTo(request.address());
        assertThat(result.getPhoneNumber()).isEqualTo(request.phoneNumber());
        assertThat(result.getLinkImgProfile()).isEqualTo(request.linkImgProfile());

        verify(passwordEncoder, never()).encode(any());
        verify(repository).save(profile);
    }

    @Test
    void should_not_encode_password_when_password_is_null()
            throws ProfileNotFoundException, ProfileNotAllowedException {
        // GIVEN
        Profile profile = givenProfile1();
        String originalPassword = profile.getPassword();

        ProfileUpdateRequest request = givenProfileUpdateRequestWithNullPassword();

        when(repository.getById(profile.getId()))
                .thenReturn(Optional.of(profile));

        when(repository.save(profile))
                .thenReturn(profile);

        // WHEN
        Profile result = service.update(profile, profile.getId(), request);

        // THEN
        assertThat(profile.getPassword()).isEqualTo(originalPassword);

        assertThat(result.getFirstName()).isEqualTo(request.firstName());
        assertThat(result.getLastName()).isEqualTo(request.lastName());
        assertThat(result.getPseudo()).isEqualTo(request.pseudo());
        assertThat(result.getEmail()).isEqualTo(request.email());
        assertThat(result.getCity()).isEqualTo(request.city());
        assertThat(result.getPostalCode()).isEqualTo(request.postalCode());
        assertThat(result.getAddress()).isEqualTo(request.address());
        assertThat(result.getPhoneNumber()).isEqualTo(request.phoneNumber());
        assertThat(result.getLinkImgProfile()).isEqualTo(request.linkImgProfile());

        verify(passwordEncoder, never()).encode(any());
        verify(repository).save(profile);
    }

    @Test
    void should_allow_admin_to_update_another_profile() throws ProfileNotFoundException, ProfileNotAllowedException {
        // GIVEN
        Profile admin = givenProfile1();
        admin.setRole(ProfileRole.ADMIN);

        UUID targetProfileId = givenUUID();

        Profile targetProfile = givenProfile2();

        ProfileUpdateRequest request = givenProfileUpdateRequest();

        when(repository.getById(targetProfileId))
                .thenReturn(Optional.of(targetProfile));

        when(passwordEncoder.encode(request.password()))
                .thenReturn("encodedPassword");

        when(repository.save(targetProfile))
                .thenReturn(targetProfile);

        // WHEN
        service.update(admin, targetProfileId, request);

        // THEN
        verify(repository).save(targetProfile);
    }

    @Test
    void should_throw_exception_when_user_updates_another_profile_without_admin_rights() {
        // GIVEN
        Profile connectedUser = givenProfile1();

        UUID targetProfileId = givenUUID();

        ProfileUpdateRequest request = givenProfileUpdateRequest();

        // WHEN / THEN
        assertThatThrownBy(() ->
                service.update(connectedUser, targetProfileId, request))
                .isInstanceOf(ProfileNotAllowedException.class)
                .hasMessage("You are not allowed to modify this profile");

        verify(repository, never()).getById(any());
        verify(repository, never()).save(any());
    }

    @Test
    void should_delete_profile() throws ProfileNotFoundException {
        // GIVEN
        UUID id = givenUUID();

        when(repository.getById(id))
                .thenReturn(Optional.of(givenProfile1()));

        // WHEN
        service.delete(id);

        // THEN
        verify(repository).delete(id);
    }

    @Test
    void should_throw_exception_when_deleting_unknown_profile() {
        // GIVEN
        UUID id = givenUUID();

        when(repository.getById(id))
                .thenReturn(Optional.empty());

        // WHEN / THEN
        assertThatThrownBy(() -> service.delete(id))
                .isInstanceOf(ProfileNotFoundException.class)
                .hasMessage("Profile with id " + id + " not found");

        verify(repository, never()).delete(any());
    }
}
