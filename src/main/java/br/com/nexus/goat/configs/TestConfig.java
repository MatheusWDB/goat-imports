package br.com.nexus.goat.configs;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.nexus.goat.entities.Category;
import br.com.nexus.goat.entities.Feature;
import br.com.nexus.goat.entities.Product;
import br.com.nexus.goat.entities.User;
import br.com.nexus.goat.repositories.CategoryRepository;
import br.com.nexus.goat.repositories.FeatureRepository;
import br.com.nexus.goat.repositories.ProductRepository;
import br.com.nexus.goat.repositories.UserRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

        @Autowired
        private CategoryRepository categoryRepository;

        @Autowired
        private ProductRepository productRepository;

        @Autowired
        private FeatureRepository featureRepository;

        @Autowired
        private UserRepository userRepository;

        @Value("${user.test}")
        private String[] user;

        @Override
        public void run(String... args) throws Exception {
                User u1 = new User(null, user[0], user[1], user[2], user[3], null, user[4]);

                userRepository.save(u1);

                Category c1 = new Category("Roupa");
                Category c2 = new Category("Camiseta");
                Category c3 = new Category("Calça");
                Category c4 = new Category("Calçados");
                Category c5 = new Category("Tênis");
                Category c6 = new Category("Camisa");
                Category c7 = new Category("Bermuda");
                Category c8 = new Category("Acessórios");
                Category c9 = new Category("Bonés");
                Category c10 = new Category("Chuteira");

                categoryRepository.saveAll(Arrays.asList(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10));

                Feature f1 = new Feature("Nike", "Nike Sportswear Icon Futura", "100% algodão",
                                "Preta com logo branca");
                Feature f2 = new Feature("Anti Social Club", "Camiseta Anti Social Club Dragão", "100% Algodão",
                                "Preto com estampa");
                Feature f3 = new Feature("Hype", "Cargo", "100% Algodão", "Preto com detalhe branco");
                Feature f4 = new Feature(
                                "Nike",
                                "Air Force",
                                "Couro sintético",
                                "Azul marinho");
                Feature f5 = new Feature(
                                "Nike",
                                "Air Force",
                                "Jeans",
                                "Jeans com Glitter e detalhes Azul");
                Feature f6 = new Feature(
                                "Under Armour",
                                "Buzzer SE",
                                "Sintético e Têxtil",
                                "Branco/Preto/Roxo");
                Feature f7 = new Feature(
                                "Adidas",
                                "Flamengo 2024/2025",
                                "Tecnologia Aeroready e Poliéster",
                                "Vermelho e Preto");
                Feature f8 = new Feature(
                                "Importada",
                                "Camisa Seleção Japão Anime",
                                "Aeroswift e 100% Poliéster",
                                "Escuro e Bordado");
                Feature f9 = new Feature(
                                "Under Armour",
                                "Curry 11",
                                "Sintético,Têxtil",
                                "Azul");
                Feature f10 = new Feature(
                                "Under Armour",
                                "Curry 4",
                                "Malha Knit, Overlays de TPU",
                                "Azul");
                Feature f11 = new Feature(
                                "Under Armour",
                                "Curry 11",
                                "UA IntelliKnit, PU moldado em 3D",
                                "Cinza");
                Feature f12 = new Feature(
                                "Under Armour",
                                "Curry 2 Retrô",
                                "Couro Sintético, Malha (Mesh)",
                                "Branco");
                Feature f13 = new Feature(
                                "Under Armour",
                                "Curry Splash",
                                "100% Poliéster",
                                "Branco");
                Feature f14 = new Feature(
                                "Ange",
                                "Aesthetic",
                                "100% Algodão",
                                "Preto");
                Feature f15 = new Feature(
                                "9Forty",
                                "New York",
                                "Tecido e Algodão",
                                "Bege");
                Feature f16 = new Feature(
                                "Ange",
                                "College",
                                "100% Algodão",
                                "Branco");
                Feature f17 = new Feature(
                                "Opus",
                                "Canelada",
                                "Viscose e Elastano",
                                "Verde Militar");
                Feature f18 = new Feature(
                                "Adidas",
                                "NMD 1",
                                "100% Algodão",
                                "Branco");
                Feature f19 = new Feature(
                                "Penalty",
                                "Society",
                                "Material Sintético",
                                "Preta com Detalhes Coloridos");

                featureRepository.saveAll(Arrays.asList(f1, f2, f3, f4, f5, f6, f7, f8, f9, f10, f11, f12, f13, f14,
                                f15, f16, f17, f18, f19));

                Product p1 = new Product("Camiseta Nike Sportswear",
                                "Camiseta preta com o logo da Nike em destaque na parte frontal, ideal para atividades esportivas e uso casual. Confortável e de alta durabilidade.",
                                129.99, 50, "https://m.media-amazon.com/images/I/81Dy7WREnNL._AC_SX522_.jpg");
                Product p2 = new Product("Camiseta Anti Social Club Dragão",
                                "é uma peça exclusiva da marca de streetwear Anti Social Social Club, famosa por suas referências à cultura pop e estilo urbano. Esta camiseta em particular apresenta um design impactante, que combina o logo da marca com a imagem de um dragão, evocando uma estética ousada e inspirada na cultura asiática e no misticismo.",
                                228.99, 50,
                                "https://cdn.awsli.com.br/300x300/1100/1100536/produto/54955712/ff1886f860.jpg");
                Product p3 = new Product("Calça Cargo Hype Unisex Preta",
                                "é uma peça de vestuário de estilo utilitário, projetada tanto para homens quanto para mulheres. Geralmente confeccionada em tecido de algodão ou poliéster, sua principal característica são os bolsos grandes e funcionais, localizados nas laterais das pernas. Isso adiciona um toque prático e estético, criando um visual casual e moderno.",
                                228.99, 50,
                                "https://dcdn.mitiendanube.com/stores/003/009/111/products/whatsapp-image-2024-01-30-at-18-24-51-4-dc46dfe1e5bce118da17066554408440-640-0.jpeg");
                Product p4 = new Product(
                                "Tênis Masculino Casual Air Force Azul Marinho",
                                "É um modelo inspirado no clássico design da linha Air Force, reconhecido pelo seu estilo atemporal e versátil. Com uma pegada urbana e contemporânea, ele é ideal tanto para looks casuais quanto esportivos.",
                                219.99,
                                60,
                                "https://www.goatshoes.com.br/cdn/shop/files/GS01S21_NikeMasculinoCasualAIRFORCEAZULMARINHO_7.jpg?v=1723320596");
                Product p5 = new Product(
                                "Tênis Nike Air Force Jeans Glitter Premium",
                                "Tênis Nike Air Force Jeans com Glitter, detalhes em azul. Modelo é Linha Premium com acabamento impecável.",
                                188.99,
                                40,
                                "https://cdn.dooca.store/63241/products/img-20231217-172802-418.jpg?v=1702845856");
                Product p6 = new Product(
                                "Tênis Masculino Under Armour Buzzer SE",
                                "A Under Armour apresenta o Tênis Masculino Under Armour Buzzer SE, ideal para quem busca conforto e qualidade em seus treinos e partidas de basquete. Com cabedal produzido em material têxtil respirável, o calçado traz solado antiderrapante, além de design moderno para seus jogos. Tem uma espuma leve que contribui para o retorno de energia, transformando aterrissagens amortecidas em decolagens explosivas.",
                                249.99,
                                70,
                                "https://cdn.dooca.store/100/products/tenis-under-armour-buzzer-se-branco-preto-roxo-2_640x640+fill_ffffff.jpg?v=1691442457&webp=0");
                Product p7 = new Product(
                                "Camisa Flamengo I 24/25 Torcedor Adidas Masculina - Vermelho+Preto",
                                "Renove sua paixão rubro-negra com a Camisa Flamengo I 24/25 s/n° Torcedor Adidas Masculina! Mesclando o clássico com toques de modernidade, essa camisa do Flamengo apresenta listras horizontais vermelhas que vão diminuindo a espessura conforme descem o manto. Além disso, possui gola V preta com detalhe frontal em vermelho, escudo bordado do lado esquerdo do peito e mangas pretas com punho em prateado e vermelho. O tecido com tecnologia Aeroready afasta o suor e amplia a sensação de conforto enquanto você joga ou assiste a bola rolar. Mostre sua paixão pelo Mengão e compre já essa camisa Flamengo masculina!",
                                199.99, 80,
                                "https://acdn.mitiendanube.com/stores/003/429/422/products/big2-f7f1ad84cc11498fd717087847141942-1024-1024.png");
                Product p8 = new Product(
                                "Camisa Seleção Japão Anime 23/24 Branca e Preta",
                                "A camisa da Seleção do Japão Anime 23/24, branca com detalhes pretos, combina futebol e cultura pop japonesa. O design traz gráficos inspirados em animes, escudo bordado e logo do fornecedor. Feita em tecido leve e respirável, oferece conforto com modelagem slim ou regular, gola em V ou redonda e mangas com detalhes estilizados, unindo estilo e performance.",
                                149.99, 40,
                                "https://acdn.mitiendanube.com/stores/001/436/693/products/img_0499-24059ba28ee3b93d0217011961393521-640-0.webp");
                Product p9 = new Product(
                                "Tênis de Basquete Under Armour Curry 11 Mouthguard",
                                "O Curry 11 é projetado especificamente com o máximo de salto, aderência e estabilidade para permitir que todos façam o que querem. Leve e confortável, o UA IntelliKnit é respirável e oferece elasticidade e suporte onde você precisa.",
                                1399.99, 30,
                                "https://underarmourbr.vtexassets.com/arquivos/ids/342536-1200-auto?v=638617476029270000&width=1200&height=auto&aspect=true");
                Product p10 = new Product(
                                "Tênis de Basquete Masculino Under Armour Curry 4 Low Flotro",
                                "O Curry 4 tem um visual mais minimalista e elegante, com uma silhueta baixa que proporciona maior mobilidade no tornozelo, mantendo um ajuste firme e seguro.",
                                1199.99,
                                30,
                                "https://underarmourbr.vtexassets.com/arquivos/ids/330631-1200-auto?v=638453225705600000&width=1200&height=auto&aspect=true");
                Product p11 = new Product(
                                "Tênis de Basquete Under Armour Curry 11 Future Wolf",
                                "Nomeado em homenagem ao filho de Stephen, Canon, ‘Future Wolf’ é sobre a próxima geração. Com o Curry 11 incrivelmente saltitante, aderente e estável, projetado especificamente para ajudar todos a fazerem suas coisas, não há como dizer o que o futuro reserva.",
                                1399.99,
                                30,
                                "https://underarmourbr.vtexassets.com/arquivos/ids/335922-1200-auto?v=638543140088900000&width=1200&height=auto&aspect=true");
                Product p12 = new Product(
                                "Tênis de Basquete Under Armour Curry 2 Retrô",
                                "Domine a quadra no Curry 2s original – de volta para um relançamento especial e limitado. Torne cada corte mais poderoso com amortecimento absorvente de choque e ajuste personalizado de uma parte superior UA Speedform que abraça os pés.",
                                1299.99,
                                30,
                                "https://underarmourbr.vtexassets.com/arquivos/ids/331510-1200-auto?v=638470594165570000&width=1200&height=auto&aspect=true");
                Product p13 = new Product(
                                "Bermuda de Basquete Masculina Under Armour Curry Splash",
                                "A Bermuda Under Armour Curry Splash é um modelo projetado para proporcionar conforto e desempenho durante a prática de basquete, com design inspirado no estilo e na performance de Stephen Curry.",
                                249.99,
                                30,
                                "https://underarmourbr.vtexassets.com/arquivos/ids/331102-1200-auto?v=638467243843930000&width=1200&height=auto&aspect=true");
                Product p14 = new Product(
                                "Boné Preto Aesthetic",
                                "O Boné Preto Aesthetic é um acessório clássico e versátil que complementa seu estilo com um toque casual e esportivo. Feito com materiais de qualidade, oferece conforto e proteção contra os raios solares. Perfeito para adicionar um toque despojado ao seu visual, seja durante atividades ao ar livre ou para complementar seu estilo do dia a dia.",
                                62.51,
                                85,
                                "https://www.useange.com.br/app/assets/images/dinamica/produto/2328/cor_0/bn25216-2-bone-preto-aesthetic-300523-17d7b5.jpg?1689605278");
                Product p15 = new Product(
                                "Boné 940 New York Yankees New Era",
                                "Com design clássico, torcer pelo time favorito ficou muito mais estiloso e confortável com esse incrível Boné New York Yankees Aba Curva 940. Reforçado por costura na parte interna, possui faixa em tecido que ajuda na absorção excessiva do suor. Já a aba é curva para maior proteção com prático fecho regulável por pinos, estilo snapback. Já o icônico escudo se destaca no painel frontal para representar a paixão pelo esporte em qualquer atividade.",
                                199.90,
                                45,
                                "https://imgcentauro-a.akamaihd.net/1366x1366/96750112.jpg");
                Product p16 = new Product(
                                "Boné Branco College",
                                "Com design clean e elegante, o Boné College é uma homenagem ao estilo tenniscore, trazendo uma estética retrô que nunca sai de moda. A simplicidade marcante da letra \"A\" bordada na frente, em uma fonte clássica, é o detalhe perfeito para quem busca um visual discreto, mas cheio de personalidade.",
                                62.51,
                                65,
                                "https://www.useange.com.br/app/assets/images/dinamica/produto/3300/cor_0/thumb/xbone-branco-college-bn25218-branco-091024-12009c.jpg,q1728499958.pagespeed.ic.4cQ70UrzG1.webp");
                Product p17 = new Product(
                                "Camiseta Canelada Gola Média Verde Militar",
                                "Camiseta em malha canelada com a gola média. Não há costura de bainha na manga e na barra, devido à um processo moderno de corte feito com máquina à laser.",
                                65.90,
                                70,
                                "https://images.tcdn.com.br/img/img_prod/727959/camiseta_canelada_gola_media_verde_militar_809_1_39f42d01dbb96a6ca726e6a4159dee88.jpg");
                Product p18 = new Product(
                                "Tênis NMD Masculino Adidas Branco",
                                "Atualização de um tênis de corrida dos anos 80 dos arquivos da adidas, este tênis moderno tem cabedal de malha macia e elástica para conforto o dia todo. Cores ousadas e plugues característicos na entressola marcam presença, fazendo você se destacar com estilo em qualquer lugar.",
                                248.90,
                                50,
                                "https://dcdn.mitiendanube.com/stores/002/615/066/products/branco-011-e7b7a468a302eb3cff16844600201997-480-0.jpeg");
                Product p19 = new Product(
                                "Chuteira Society Penalty Storm Y-3",
                                "A Chuteira Society Penalty Storm Y-3 é a escolha definitiva para os apaixonados pelo futebol society. Com um design arrojado e tecnologia de ponta, esta chuteira proporciona um desempenho superior em campo. Desenvolvida com solado de borracha de alta qualidade, garante aderência excepcional em todas as direções, permitindo movimentos ágeis e precisos.",
                                219.90,
                                45,
                                "https://cdnv2.moovin.com.br/bedin/imagens/produtos/det/chuteira-society-penalty-storm-y-3-88decd7ddd60e49652687182b18fdefc.jpeg");

                p1.setFeatures(f1);
                p1.getCategories().add(c1);
                p1.getCategories().add(c2);

                p2.setFeatures(f2);
                p2.getCategories().add(c1);
                p2.getCategories().add(c2);

                p3.setFeatures(f3);
                p3.getCategories().add(c1);
                p3.getCategories().add(c3);

                p4.setFeatures(f4);
                p4.getCategories().add(c4);
                p4.getCategories().add(c5);

                p5.setFeatures(f5);
                p5.getCategories().add(c4);
                p5.getCategories().add(c5);

                p6.setFeatures(f6);
                p6.getCategories().add(c4);
                p6.getCategories().add(c5);

                p7.setFeatures(f7);
                p7.getCategories().add(c1);
                p7.getCategories().add(c6);

                p8.setFeatures(f8);
                p8.getCategories().add(c1);
                p8.getCategories().add(c6);

                p9.setFeatures(f9);
                p9.getCategories().add(c4);
                p9.getCategories().add(c5);

                p10.setFeatures(f10);
                p10.getCategories().add(c4);
                p10.getCategories().add(c5);

                p11.setFeatures(f11);
                p11.getCategories().add(c4);
                p11.getCategories().add(c5);

                p12.setFeatures(f12);
                p12.getCategories().add(c4);
                p12.getCategories().add(c5);

                p13.setFeatures(f13);
                p13.getCategories().add(c1);
                p13.getCategories().add(c7);

                p14.setFeatures(f14);
                p14.getCategories().add(c8);
                p14.getCategories().add(c9);

                p15.setFeatures(f15);
                p15.getCategories().add(c8);
                p15.getCategories().add(c9);

                p16.setFeatures(f16);
                p16.getCategories().add(c8);
                p16.getCategories().add(c9);

                p17.setFeatures(f17);
                p17.getCategories().add(c1);
                p17.getCategories().add(c6);

                p18.setFeatures(f18);
                p18.getCategories().add(c4);
                p18.getCategories().add(c5);

                p19.setFeatures(f19);
                p19.getCategories().add(c4);
                p19.getCategories().add(c10);

                productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14,
                                p15, p16, p17, p18, p19));
        }
}
