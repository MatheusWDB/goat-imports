// INICIA ANTES DE TUDO
const valor_quantia = document.querySelector('.quantia');
var quantia = 0
var products
var categories
const userId = localStorage.getItem('authUserId');
var items = localStorage.getItem('carrinho') === null ?
    [] :
    JSON.parse(localStorage.getItem('carrinho'))
const urlLocal = "http://localhost:8080"
const url = "https://goatimports.onrender.com"
const catalogo = document.getElementById('catalogo');
const categorias = document.getElementById('categoria');
checkAuthUserId()
const radios = document.querySelectorAll('input[name="tamanho"]');


function checkAuthUserId() {
    if (!userId) {
        alert("Usuário não autenticado. Redirecionando para a página de login.")
        logout()
    } else {
        buscarTodasCategorias()
        buscarTodosProdutos()
    }
}
// INICIA ANTES DE TUDO

async function buscarTodasCategorias() {
    try {
        document.getElementById('favicon').setAttribute('href', '../imagens/spinner.gif');
        document.getElementById('loading-overlay').style.display = 'flex';
        const response = await fetch(`${url}/categories/findAll`, {
            method: 'GET'
        })

        if (response.ok) {
            const data = await response.json();
            categories = data
            renderizarListaCategorias(categories)
        } else {
            const error = await response.json();
            console.log(error)
            alert(error.message);
        }
        document.getElementById('favicon').setAttribute('href', '../imagens/logo2.png');
        document.getElementById('loading-overlay').style.display = 'none';
    } catch (error) {
        console.log('Erro ao fazer a requisição: ', error);
        document.getElementById('favicon').setAttribute('href', '../imagens/logo2.png');
        document.getElementById('loading-overlay').style.display = 'none';
        alert('Erro no servidor!' + error.message);
    }
}

async function buscarTodosProdutos() {
    try {
        document.getElementById('favicon').setAttribute('href', '../imagens/spinner.gif');
        document.getElementById('loading-overlay').style.display = 'flex';
        const response = await fetch(`${url}/products/findAll`, {
            method: 'GET',
        })

        if (response.ok) {
            const data = await response.json();
            products = data
            renderizarListaProdutos(products)
        } else {
            const error = await response.json();
            console.log(error)
            alert(error.message);
        }
        document.getElementById('favicon').setAttribute('href', '../imagens/logo2.png');
        document.getElementById('loading-overlay').style.display = 'none';
    } catch (error) {
        console.log('Erro ao fazer a requisição: ', error);
        document.getElementById('loading-overlay').style.display = 'none';
        document.getElementById('favicon').setAttribute('href', '../imagens/logo2.png');
        alert('Erro no servidor!' + error.message);
    }
}

const renderizarListaCategorias = (categories) => {
    categorias.innerHTML = ''

    const button = document.createElement('button')
    button.classList.add('button-categoria')
    button.onclick = () => filtro('all')

    const card = document.createElement('button')
    card.classList.add('cardcategoria')
    card.textContent = 'Tudo'

    button.appendChild(card)
    categorias.appendChild(button)

    categories.forEach(category => {
        const button = document.createElement('button')
        button.classList.add('button-categoria')

        button.onclick = () => filtro(category.id)

        const card = document.createElement('button')
        card.classList.add('cardcategoria')
        card.textContent = category.name

        button.appendChild(card)
        categorias.appendChild(button)
    });
}

const filtro = (idCategoria) => {
    if (idCategoria === 'all') {
        renderizarListaProdutos(products)
    } else {
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

}

const renderizarListaProdutos = (products) => {
    catalogo.innerHTML = ''

    products.forEach(product => {
        const button = document.createElement('button')
        button.classList.add('button-card')
        button.onclick = () => selecionarProduto(product)

        const card = document.createElement('div')
        card.id = product.id
        card.classList.add('card')

        const nome = document.createElement('p')
        nome.textContent = product.name
        nome.classList.add('nomeProduto')

        const preco = document.createElement('p')
        preco.textContent = "R$ " + product.price.toFixed(2).toString().replace(".", ",");
        preco.classList.add('precoProduto')

        const imagem = document.createElement('img');
        imagem.src = product.imgUrl;
        imagem.alt = product.name;
        imagem.classList.add('imgProduto');

        card.appendChild(imagem);
        card.appendChild(nome);
        card.appendChild(preco);

        button.appendChild(card)

        catalogo.appendChild(button)

    })
}

const selecionarProduto = async (produto) => {
    quantia = 0
    document.getElementById('modal').style.display = 'flex';
    radios.forEach(radio => radio.checked = false);

    const radioContainer = document.getElementById('formTamanho');
    radioContainer.innerHTML = ''; // Limpa os tamanhos existentes

    var categories = []

    try {
        document.getElementById('favicon').setAttribute('href', '../imagens/spinner.gif');
        document.getElementById('loading-overlay').style.display = 'flex';
        const response = await fetch(`${url}/categories/findAllByIdProduct/${produto.id}`, {
            method: 'GET'
        })

        if (response.ok) {
            const data = await response.json();
            categories = data
        } else {
            const error = await response.json();
            console.log(error)
            alert(error.message);
        }
        document.getElementById('favicon').setAttribute('href', '../imagens/logo2.png');
        document.getElementById('loading-overlay').style.display = 'none';
    } catch (error) {
        console.log('Erro ao fazer a requisição: ', error);
        document.getElementById('favicon').setAttribute('href', '../imagens/logo2.png');
        document.getElementById('loading-overlay').style.display = 'none';
        alert('Erro no servidor!' + error.message);
    }

    let tamanhos = [];
    categories.forEach(category => {
        if (category.name === 'Roupa') {
            tamanhos = ['P', 'M', 'G', 'GG', 'XG', 'XGG'];
        } else if (category.name === 'Calçados') {
            tamanhos = ['35', '36', '37', '38', '39', '40', '41', '42'];
        } else if (category.name === 'Bonés') {
            tamanhos = ['PP', 'P', 'M', 'G', 'GG'];
        }
    });

    // Verificar se tamanhos foram definidos para a categoria
    if (tamanhos.length > 0) {
        tamanhos.forEach(tamanho => {
            const label = document.createElement('label');
            label.classList.add('radio-container');
            label.innerHTML = `
                <input type="radio" name="tamanho" value="${tamanho}">
                <span class="radio-text">${tamanho}</span>
            `;
            radioContainer.appendChild(label);
        });
    } else {
        console.error('Categoria não reconhecida para tamanhos.');
    }

    const button = document.getElementById('buttonAdicionarAoCarrinho')
    button.onclick = () => {
        var tamanhoSelecionado = document.querySelector('input[name="tamanho"]:checked');
        var quantidade = parseInt(valor_quantia.textContent)
        if (!tamanhoSelecionado) {
            alert("Escolha o tamanho da peça!")
            valor_quantia.textContent = 0
        } else if (quantidade <= 0) {
            alert("Defina a quantidade!")
            valor_quantia.textContent = 0
            radios.forEach(radio => radio.checked = false);
        } else {
            adicionarAoCarrinho(produto, quantidade, tamanhoSelecionado)
        }
    }

    const imagemProdutoSelecionado = document.getElementById('imagemProdutoSelecionado')
    imagemProdutoSelecionado.src = produto.imgUrl
    imagemProdutoSelecionado.alt = produto.name

    const nomeProdutoSelecionado = document.getElementById('nomeProdutoSelecionado')

    nomeProdutoSelecionado.textContent = produto.name

    const precoProdutoSelecionado = document.getElementById('precoProdutoSelecionado')
    precoProdutoSelecionado.textContent = `Preço: R$${produto.price.toFixed(2).toString().replace(".", ",")}`

    var features

    try {
        document.getElementById('favicon').setAttribute('href', '../imagens/spinner.gif');
        document.getElementById('loading-overlay').style.display = 'flex';
        const response = await fetch(`${url}/features/findByProductId/${produto.id}`, {
            method: 'GET',
        })

        if (response.ok) {
            const data = await response.json();
            features = data
        } else {
            const error = await response.json();
            console.log(error)
            alert(error.message);
        }
        document.getElementById('favicon').setAttribute('href', '../imagens/logo2.png');
        document.getElementById('loading-overlay').style.display = 'none';
    } catch (error) {
        console.log('Erro ao fazer a requisição: ', error);
        document.getElementById('favicon').setAttribute('href', '../imagens/logo2.png');
        document.getElementById('loading-overlay').style.display = 'none';
        alert('Erro no servidor!' + error.message);
    }

    const marcaProdutoSelecionado = document.getElementById("marcaProdutoSelecionado")
    marcaProdutoSelecionado.textContent = features.mark

    const modeloProdutoSelecionado = document.getElementById("modeloProdutoSelecionado")
    modeloProdutoSelecionado.textContent = features.model

    const corProdutoSelecionado = document.getElementById("corProdutoSelecionado")
    corProdutoSelecionado.textContent = features.color

    const composicaoProdutoSelecionado = document.getElementById("composicaoProdutoSelecionado")
    composicaoProdutoSelecionado.textContent = features.composition
}

function adicionarAoCarrinho(produto, quantidade, tamanhoSelecionado) {
    var contador = true
    valor_quantia.textContent = 0
    const product = { ...produto }

    items.forEach(item => {
        if (produto.id === item.id && tamanhoSelecionado.value === item.size) {
            item.quantity += quantidade
            !contador
        }
    });

    if (contador) {
        product.size = tamanhoSelecionado.value
        product.quantity = quantidade
        items.push(product)
    }
    localStorage.setItem('carrinho', JSON.stringify(items))
    radios.forEach(radio => radio.checked = false);
    document.getElementById('modal').style.display = 'none';
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

function logout() {
    localStorage.clear();
    window.location.href = "../index.html"
}

function closemodal() {
    document.getElementById('modal').style.display = 'none';
}



let intervalId;

function adicionar() {
    quantia = parseInt(valor_quantia.textContent) + 1; //Acresecenta 1 na quantidade de lanches de indicie 'r'

    valor_quantia.textContent = quantia; //Atualiza o valor final da quantia do lanche de indicie 'r'
}

function retirar() {
    if (quantia > 0) {
        //Se a quantia for maior que zero, então...

        valor_quantia.textContent = quantia - 1; //Captura o valor atual da quantia do lanche de indicie 'r'

        quantia = parseInt(valor_quantia.textContent); //Atualiza o valor final da quantia do lanche de indicie 'r'
    }

}
function resetar() {
    if (quantia > 0) {
        valor_quantia.textContent = 0;
        quantia = 0;
    }
    //Reseta todos os valores do lanche de indicie 'r' para o valor inicial
}

function startPressing(x) {
    // Inicia a repetição da função retirar() a cada 200ms (ajuste conforme necessário)
    intervalId = x == 'retirar' ? setInterval(retirar, 200) : setInterval(adicionar, 200);
}

function stopPressing() {
    // Para a repetição quando o botão é liberado
    clearInterval(intervalId);
}

function voltarHome() {
    location.href = "#"
}