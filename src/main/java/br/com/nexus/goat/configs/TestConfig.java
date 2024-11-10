package br.com.nexus.goat.configs;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.nexus.goat.models.Category;
import br.com.nexus.goat.models.Feature;
import br.com.nexus.goat.models.Product;
import br.com.nexus.goat.repositories.CategoryRepository;
import br.com.nexus.goat.repositories.FeatureRepository;
import br.com.nexus.goat.repositories.ProductRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private FeatureRepository featureRepository;

    @Override
    public void run(String... args) throws Exception {

        Category c1 = new Category("roupa");
        Category c2 = new Category("camiseta");

        Feature f1 = new Feature("Nike", "Nike Sportswear Icon Futura", "100% algodão", "Preta com logo branca");

        this.categoryRepository.saveAll(Arrays.asList(c1, c2));
        this.featureRepository.saveAll(Arrays.asList(f1));

        Product p1 = new Product("Camiseta Nike Sportswear",
                "Camiseta preta com o logo da Nike em destaque na parte frontal, ideal para atividades esportivas e uso casual. Confortável e de alta durabilidade.",
                129.99, "GG", 50, "https://m.media-amazon.com/images/I/81Dy7WREnNL._AC_SX522_.jpg");

        p1.setFeatures(f1);
        p1.getCategories().add(c1);
        p1.getCategories().add(c2);

        this.productRepository.saveAll(Arrays.asList(p1));
    }
}
