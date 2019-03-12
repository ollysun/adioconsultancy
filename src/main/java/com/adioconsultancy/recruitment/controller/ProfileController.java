package com.adioconsultancy.recruitment.controller;

import com.adioconsultancy.recruitment.domain.Profile;
import com.adioconsultancy.recruitment.domain.User;
import com.adioconsultancy.recruitment.exception.ResourceNotFoundException;
import com.adioconsultancy.recruitment.repository.IProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api")
public class ProfileController {

    public static final Logger logger = LoggerFactory.getLogger(ProfileController.class);


    @Autowired
    IProfileRepository profileRepository;

    @GetMapping("/profile")
    public ResponseEntity<Iterable<Profile>> getProfiles(){
        return ResponseEntity.ok().body(profileRepository.findAll());
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity getProfileById(@PathVariable(value = "id") final Long id) throws ResourceNotFoundException {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found on :: "+ id));
        return ResponseEntity.ok().body(profile);
    }

    @RequestMapping(value="/profile/create", method = RequestMethod.POST)
    public ResponseEntity<?> createProfile(
            @RequestBody Profile profile, UriComponentsBuilder ucBuilder){
        logger.info("Creating User : {}", profile);
        if (profileRepository.existsByEmail(profile.getEmail())) {
            logger.error("Unable to create. A User with email already exist", profile.getEmail());
            return new ResponseEntity<>("Unable to create. A User with name "
                    + profile.getEmail() + " already exist.", HttpStatus.CONFLICT);
        }
        Profile newprofile = new Profile(profile.getFirstame(),
                                         profile.getSurname(),
                                         profile.getPhoneno(),
                                         profile.getEmail(),
                                         profile.getCoverletter(),
                                         profile.getResumefilename(),
                                         profile.getPhotofilename());
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/profile/{id}").buildAndExpand(profile.getId()).toUri());
        return new ResponseEntity<>(profileRepository.save(newprofile), HttpStatus.OK);
    }
}
