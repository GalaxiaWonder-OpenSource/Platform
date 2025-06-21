ALTER TABLE user_accounts
    ADD CONSTRAINT fk_user_person
        FOREIGN KEY (person_id)
            REFERENCES persons(id);

ALTER TABLE organization_members
    ADD CONSTRAINT fk_member_person
        FOREIGN KEY (person_id)
            REFERENCES persons(id);

ALTER TABLE organization_invitations
    ADD CONSTRAINT fk_invitation_person
        FOREIGN KEY (person_id)
            REFERENCES persons(id);