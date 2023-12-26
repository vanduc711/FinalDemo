package com.tutorial.finaldemo.config;

import com.tutorial.finaldemo.entity.Product;
import com.tutorial.finaldemo.reponsitory.ProductReponsitory;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@AllArgsConstructor
public class ProductBatchConfig {

    @Autowired
    ProductReponsitory productReponsitory;

    @Bean
    public FlatFileItemReader<Product> reader() {
        return new FlatFileItemReaderBuilder<Product>()
                .name("ProductsReader")
                .resource(new FileSystemResource("src/main/resources/product-list.csv"))
                .lineMapper(LineMapper())
                .linesToSkip(1)
                .build();
    }

    @Bean
    public RepositoryItemWriter<Product> writer() {
                RepositoryItemWriter<Product> writer = new RepositoryItemWriter<>();
                writer.setRepository(productReponsitory);
                writer.setMethodName("save");
                return writer;
    }

    private LineMapper<Product> LineMapper() {
                DefaultLineMapper<Product> lineMapper = new DefaultLineMapper<>();
                DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
                lineTokenizer.setDelimiter(",");
                lineTokenizer.setStrict(false);
                lineTokenizer.setNames("id", "name", "price", "category_id");
                lineMapper.setLineTokenizer(lineTokenizer);
                lineMapper.setFieldSetMapper(new CustomFieldSetMapper());
                return lineMapper;
    }

    public static class CustomFieldSetMapper implements FieldSetMapper<Product> {
            @NotNull
            @Override
            public Product mapFieldSet(FieldSet fieldSet) {
                Product product = new Product();
                product.setId(fieldSet.readInt("id"));
                product.setName(fieldSet.readString("name"));
                product.setPrice(fieldSet.readInt("price"));
                product.setCategory(fieldSet.readInt("category_id"));
                return product;
            }
    }


    @Bean
    public ItemProcessor<Product, Product> processor() {
            return new ItemProcessor<Product, Product>() {
                @Override
                public Product process(Product item) throws Exception {
                    return item;
                }
            };
    }

    @Bean
    @Primary
    public Job importProductJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
            return new JobBuilder("importProductJob", jobRepository)
                    .flow(step2(jobRepository, transactionManager))
                    .end()
                    .build();
    }

    @Bean
    public Step step2(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
            return new StepBuilder("step2", jobRepository)
                    //chunk để tăng hiệu xuất khi xử lý dữ liệu
                    .<Product, Product>chunk(10, transactionManager)
                    // Chỉ định ItemReader được sử dụng để đọc dữ liệu.
                    .reader(reader())
                    //Chỉ định ItemProcessor được sử dụng để thực hiện xử lý trung gian trên dữ liệu
                    .processor(processor())
                    //Chỉ định ItemWriter được sử dụng để ghi dữ liệu vào cơ sở dữ liệu
                    .writer(writer())
                    .build();
    }
}

