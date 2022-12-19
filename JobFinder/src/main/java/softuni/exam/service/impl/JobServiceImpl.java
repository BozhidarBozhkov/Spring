package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.JobDto;
import softuni.exam.models.dto.JobImportWrapperDto;
import softuni.exam.models.entity.Company;
import softuni.exam.models.entity.Job;
import softuni.exam.repository.CompanyRepository;
import softuni.exam.repository.JobRepository;
import softuni.exam.service.JobService;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {
    private final Double SALARY = 5000.00;
    private final Double HOURSAWEEK = 30.00;
    private final Path path = Path.of("src/main/resources/files/xml/jobs.xml");

    private final JobRepository jobRepository;
    private final CompanyRepository companyRepository;


    private final Unmarshaller unmarshaller;
    private final Validator validator;
    private final ModelMapper modelMapper;

    @Autowired
    public JobServiceImpl(JobRepository jobRepository, CompanyRepository companyRepository) throws JAXBException {
        this.jobRepository = jobRepository;
        this.companyRepository = companyRepository;

        JAXBContext context = JAXBContext.newInstance(JobImportWrapperDto.class);
        this.unmarshaller = context.createUnmarshaller();

        this.validator = Validation
                .buildDefaultValidatorFactory()
                .getValidator();

        this.modelMapper = new ModelMapper();
    }

    @Override
    public boolean areImported() {
        return this.jobRepository.count() > 0;
    }

    @Override
    public String readJobsFileContent() throws IOException {
        return Files.readString(path);
    }

    @Override
    public String importJobs() throws IOException, JAXBException {
        JobImportWrapperDto jobDtos = (JobImportWrapperDto) this.unmarshaller.unmarshal(new FileReader(path.toAbsolutePath().toString()));

        return jobDtos.getJobs().stream()
                .map(this::importJob)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    @Override
    public String getBestJobs() {

        List<Job> jobs = this.jobRepository.findAllBySalaryGreaterThanAndHoursAWeekLessThanOrderBySalaryDesc(SALARY, HOURSAWEEK);

        return jobs.stream().map(Job::toString).collect(Collectors.joining(System.lineSeparator()));


    }

    private String importJob(JobDto dto) {

        Set<ConstraintViolation<JobDto>> errors = this.validator.validate(dto);
        if (!errors.isEmpty()) {
            return "Invalid job";
        }

        Optional<Job> optionalJob = this.jobRepository.findByTitle(dto.getTitle());
        if (optionalJob.isPresent()) {
            return "Invalid job";
        }

        Job job = this.modelMapper.map(dto, Job.class);
        Optional<Company> optionalCompany = this.companyRepository.findById(dto.getCompany());
        if (optionalCompany.isPresent()) {
            job.setCompany(optionalCompany.get());
            this.jobRepository.saveAndFlush(job);
        }
        return String.format("Successfully imported job %s", job.getTitle());
    }
}
