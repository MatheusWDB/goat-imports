package br.com.nexus.goat.configs;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.nexus.goat.entity.Category;
import br.com.nexus.goat.entity.Feature;
import br.com.nexus.goat.entity.Product;
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
                Category c3 = new Category("calça");

                this.categoryRepository.saveAll(Arrays.asList(c1, c2, c3));

                Feature f1 = new Feature("Nike", "Nike Sportswear Icon Futura", "100% algodão",
                                "Preta com logo branca");
                Feature f2 = new Feature("Anti Social Club", "Camiseta Anti Social Club Dragão", "100% Algodão",
                                "Preto com estampa");
                Feature f3 = new Feature("Hype", "Cargo", "100% Algodão", "Preto com detalhe branco");

                this.featureRepository.saveAll(Arrays.asList(f1, f2, f3));

                Product p1 = new Product("Camiseta Nike Sportswear",
                                "Camiseta preta com o logo da Nike em destaque na parte frontal, ideal para atividades esportivas e uso casual. Confortável e de alta durabilidade.",
                                129.99, 50, "https://m.media-amazon.com/images/I/81Dy7WREnNL._AC_SX522_.jpg");
                Product p2 = new Product("Camiseta Anti Social Club Dragão",
                                "é uma peça exclusiva da marca de streetwear Anti Social Social Club, famosa por suas referências à cultura pop e estilo urbano. Esta camiseta em particular apresenta um design impactante, que combina o logo da marca com a imagem de um dragão, evocando uma estética ousada e inspirada na cultura asiática e no misticismo.",
                                228.99, 50, null);
                Product p3 = new Product("Calça Cargo Hype Unisex Preta",
                                "é uma peça de vestuário de estilo utilitário, projetada tanto para homens quanto para mulheres. Geralmente confeccionada em tecido de algodão ou poliéster, sua principal característica são os bolsos grandes e funcionais, localizados nas laterais das pernas. Isso adiciona um toque prático e estético, criando um visual casual e moderno.",
                                228.99, 50, null);

                p1.setFeatures(f1);
                p1.getCategories().add(c1);
                p1.getCategories().add(c2);

                p2.setFeatures(f2);
                p2.getCategories().add(c1);
                p2.getCategories().add(c2);

                p3.setFeatures(f3);
                p3.getCategories().add(c1);
                p3.getCategories().add(c3);

                this.productRepository.saveAll(Arrays.asList(p1, p2, p3));
        }
}
