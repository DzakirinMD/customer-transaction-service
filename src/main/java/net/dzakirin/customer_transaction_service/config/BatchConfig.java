package net.dzakirin.customer_transaction_service.config;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import net.dzakirin.customer_transaction_service.listener.JobCompletionListener;
import net.dzakirin.customer_transaction_service.mapper.TransactionFieldSetMapper;
import net.dzakirin.customer_transaction_service.model.Transaction;
import net.dzakirin.customer_transaction_service.repository.TransactionRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final TransactionRepository transactionRepository;

    // start::reader[]
    @Bean
    public FlatFileItemReader<Transaction> reader() {
        FlatFileItemReader<Transaction> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new FileSystemResource("src/main/resources/dataSource.csv"));
        flatFileItemReader.setName("FileReader");
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setLineMapper(lineMapper());

        return flatFileItemReader;
    }

    private LineMapper<Transaction> lineMapper() {
        DefaultLineMapper<Transaction> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("accountNumber", "trxAmount", "description", "transactionDate", "transactionTime", "customerId");
        BeanWrapperFieldSetMapper<Transaction> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Transaction.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(new TransactionFieldSetMapper());

        return lineMapper;
    }
    // end::reader[]

    // start::processor[]
    @Bean
    public ItemProcessor<Transaction, Transaction> processor() {
        return transaction -> {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

            transaction.setTransactionDate(LocalDate.parse(transaction.getTransactionDate().toString(), dateFormatter));
            transaction.setTransactionTime(LocalTime.parse(transaction.getTransactionTime().toString(), timeFormatter));

            return transaction;
        };
    }
    // end::processor[]

    // start::writer[]
    @Bean
    public RepositoryItemWriter<Transaction> writer() {
        RepositoryItemWriter<Transaction> writer = new RepositoryItemWriter<>();
        writer.setRepository(transactionRepository);
        writer.setMethodName("save");

        return writer;
    }
    // end::writer[]

    // start::step[]
    @Bean
    public Step step1(){

        // process 10 record at a time. taskExecutor is used to process in 10 thread parallel
        return stepBuilderFactory.get("extract-step").<Transaction,Transaction>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }
    // end::step[]

    // start::job[]
    /**
     * Batch job to process the step
     */
    @Bean
    public Job runCSVJob() {
        return jobBuilderFactory.get("import-transaction")
                .flow(step1())
                .end()
                .listener(jobCompletionListener())
                .build();
    }
    // end::job[]

    // start::listener[]
    @Bean
    public JobCompletionListener jobCompletionListener() {
        return new JobCompletionListener();
    }
    // end::listener[]

    // start::taskexecutor[]
    @Bean
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
        asyncTaskExecutor.setConcurrencyLimit(10);
        return asyncTaskExecutor;
    }
    // end::taskexecutor[]
}
