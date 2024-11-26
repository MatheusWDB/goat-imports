// INICIA ANTES DE TUDO
checkAuthUserId()
// INICIA ANTES DE TUDO

var userId
var products
var categories

const catalogo = document.getElementById('catalogo');
const categorias = document.getElementById('categoria');

function checkAuthUserId() {
    userId = localStorage.getItem('authUserId');
    console.log(userId)

    if (!userId) {
        alert("Usuário não autenticado. Redirecionando para a página de login.")
        window.location.href = "../../index.html";
    } else {
        console.log("userId carregado com sucesso")
        buscarTodasCategorias()
        buscarTodosProdutos()
    }
}

async function buscarTodasCategorias() {
    try {
        const response = await fetch("http://localhost:8080/categories/findAll", {
            method: 'GET'
        })

        if (response.ok) {
            const data = await response.json();
            categories = data
            console.log(categories)
            renderizarListaCategorias(categories)
        } else {
            const error = await response.json();
            console.log(error)
            alert(error.message);
        }
    } catch (error) {
        console.log('Erro ao fazer a requisição: ', error);
        alert('Erro no servidor!' + error.message);
    }
}

async function buscarTodosProdutos() {
    try {
        const response = await fetch("http://localhost:8080/products/findAll", {
            method: 'GET',
        })

        if (response.ok) {
            const data = await response.json();
            products = data
            console.log(products)
            renderizarListaProdutos(products)
        } else {
            const error = await response.json();
            console.log(error)
            alert(error.message);
        }
    } catch (error) {
        console.log('Erro ao fazer a requisição: ', error);
        alert('Erro no servidor!' + error.message);
    }
}

const renderizarListaCategorias = (categories) => {
    categories.forEach(category => {
        const button = document.createElement('button')
        button.classList.add('button-categoria')
        
        button.onclick = () => filtro(category.id)

        const card = document.createElement('button')
        card.classList.add('card')
        card.textContent = category.name

        button.appendChild(card)

        categorias.appendChild(button)
    });
}

const filtro = (idCategoria) => {
    let productFilter = []
    products.forEach(product => {
        product.categories.forEach(idCategory => {
            if (idCategory === idCategoria) {
                productFilter.push(product)
            }
        })
    });
    renderizarListaProdutos(productFilter)
}

const renderizarListaProdutos = (products) => {
    catalogo.innerHTML = ''

    products.forEach(product => {
        const button = document.createElement('button')
        button.classList.add('button-card')
        button.onclick = () => selecionarProduto(card.id)

        const card = document.createElement('div')
        card.id = product.id
        card.classList.add('card')

        const nome = document.createElement('p')
        nome.textContent = product.name
        const descricao = document.createElement('p')
        descricao.textContent = product.description
        const preco = document.createElement('p')
        preco.textContent = "R$ " + product.price.toString().replace(".", ",")
        const tamanho = document.createElement('p')
        tamanho.textContent = product.size
        const estoque = document.createElement('p')
        estoque.textContent = product.stock
        const imagem = document.createElement('img');
        imagem.src = product.imgUrl;
        imagem.alt = product.name;
        imagem.style.width = "100%";


        card.appendChild(imagem);
        card.appendChild(nome);
        card.appendChild(preco);

        button.appendChild(card)

        catalogo.appendChild(button)

    })
}

const selecionarProduto = (idCard) => {
    window.location.href = "../produto-selecionado/produto-selecionado.html?id=" + idCard;
}

//
function scrollLeftProduct() {
    catalogo.scrollBy({
        left: -200, // A largura do seu card 
        behavior: 'smooth'
    });
}
function scrollRightProduct() {
    catalogo.scrollBy({
        left: 200, // A largura do seu card 
        behavior: 'smooth'
    });
}

function scrollLeftCategory() {
    categorias.scrollBy({
        left: -200, // A largura do seu card 
        behavior: 'smooth'
    });
}
function scrollRightCategory() {
    categorias.scrollBy({
        left: 200, // A largura do seu card 
        behavior: 'smooth'
    });
}