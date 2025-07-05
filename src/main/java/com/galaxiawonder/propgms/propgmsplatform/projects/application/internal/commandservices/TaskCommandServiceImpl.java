package com.galaxiawonder.propgms.propgmsplatform.projects.application.internal.commandservices;

import com.galaxiawonder.propgms.propgmsplatform.iam.interfaces.acl.IAMContextFacade;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates.Task;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands.CreateTaskCommand;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.commands.UpdateTaskCommand;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.*;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.services.TaskCommandService;
import com.galaxiawonder.propgms.propgmsplatform.projects.infrastructure.persistence.jpa.repositories.MilestoneRepository;
import com.galaxiawonder.propgms.propgmsplatform.projects.infrastructure.persistence.jpa.repositories.SpecialtyRepository;
import com.galaxiawonder.propgms.propgmsplatform.projects.infrastructure.persistence.jpa.repositories.TaskRepository;
import com.galaxiawonder.propgms.propgmsplatform.projects.infrastructure.persistence.jpa.repositories.TaskStatusRepository;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.MilestoneId;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.PersonId;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskCommandServiceImpl implements TaskCommandService {
    private final TaskRepository taskRepository;
    private final MilestoneRepository milestoneRepository;
    private final SpecialtyRepository specialtyRepository;
    private final TaskStatusRepository taskStatusRepository;
    private final IAMContextFacade iamContextFacade;

    public TaskCommandServiceImpl(TaskRepository taskRepository,
                                  MilestoneRepository milestoneRepository,
                                  SpecialtyRepository specialtyRepository,
                                  TaskStatusRepository taskStatusRepository,
                                  IAMContextFacade iamContextFacade) {
        this.taskRepository = taskRepository;
        this.milestoneRepository = milestoneRepository;
        this.specialtyRepository = specialtyRepository;
        this.taskStatusRepository = taskStatusRepository;
        this.iamContextFacade = iamContextFacade;
    }

    public Optional<Task> handle(CreateTaskCommand command){
        if (command == null){
            throw new IllegalArgumentException("CreateTaskCommand cannot be null");
        }

        var task = new Task(command);

        var milestone = milestoneRepository.findById(command.MilestoneId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "The milestone with the id " + command.MilestoneId() + "does not exists"
                ));

        task.validateDateRange(milestone.getDateRange());

        if (command.PersonId() != null){
            var person = iamContextFacade.getProfileDetailsById(command.PersonId());
            task.reassignPerson(new PersonId(command.PersonId()));

        }

        var specialty = specialtyRepository.findByName(Specialties.valueOf(command.Specialty()))
                .orElseThrow(() -> new IllegalArgumentException(
                        "The specialty with the name " + command.Specialty() + "does not exists"
                ));
        task.setSpecialty(specialty);

        if (command.Status() == null || command.PersonId() == null){
            var draftStatus = taskStatusRepository.findByName(TaskStatuses.DRAFT)
                    .orElseThrow(() -> new IllegalArgumentException(
                            "The task status with the name " + TaskStatuses.DRAFT + "does not exists"
                    ));
            task.toDraft(draftStatus);
        } else{
            var status = taskStatusRepository.findByName(TaskStatuses.valueOf(command.Status()))
                    .orElseThrow(() -> new IllegalArgumentException(
                            "The task status with the name " + command.Status() + "does not exists"
                    ));
            task.reassignStatus(status);
        }
        var createdTask = taskRepository.save(task);
        return Optional.of(createdTask);
    }

    public Optional<Task> handle(UpdateTaskCommand command){
        if (command == null){
            throw new IllegalArgumentException("Update Task Command cannot be null");
        }

        var task = taskRepository.findById(command.id())
                .orElseThrow(() -> new IllegalArgumentException(
                        "The task with the id " + command.id() + "does not exists"
                ));

        if (command.name() != null){
            task.reassignName(new MilestoneItemName(command.name()));
        }

        if (command.description() != null){
            task.reassignDescription(new Description(command.description()));
        }

        if (command.status() != null){
            var status = taskStatusRepository.findByName(TaskStatuses.valueOf(command.status()))
                    .orElseThrow(() -> new IllegalArgumentException(
                            "The task status with the name " + command.status() + "does not exists"
                    ));
            task.reassignStatus(status);
        }

        if (command.startDate() != null && command.endDate() != null){
            var newRangeDate = new DateRange(command.startDate(), command.endDate());
            task.validateDateRange(newRangeDate);
            task.reassignDateRange(newRangeDate);
        }

        if(command.removePerson()){
            var reassignedStatus = taskStatusRepository.findByName(TaskStatuses.DRAFT)
                    .orElseThrow(() -> new IllegalArgumentException(
                            "The task status with the name " + TaskStatuses.DRAFT + "does not exists"
                    ));
            task.toDraft(reassignedStatus);
        } else if (command.personId() != null) {
            var personId = iamContextFacade.getProfileDetailsById(command.personId());
            if (personId == null){
                throw new IllegalArgumentException("The person with the id " + command.personId() + "does not exists");
            }
            task.reassignPerson(new PersonId(command.personId()));
        }

        var updatedTask = taskRepository.save(task);
        return Optional.of(updatedTask);
    }
}
