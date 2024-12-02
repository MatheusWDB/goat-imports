// INICIA ANTES DE TUDO
const userId = localStorage.getItem('authUserId');
var items = localStorage.getItem('carrinho') === null ?
    [] :
    JSON.parse(localStorage.getItem('carrinho'))
console.log(items)
let intervalId;
document.addEventListener('DOMContentLoaded', () => {
    checkAuthUserId()
});


function checkAuthUserId() {
    if (!userId) {
        alert("Usuário não autenticado. Redirecionando para a página de login.")
        logout()
    } else {
        renderizarCarrinho()
    }
}
// INICIA ANTES DE TUDO

function renderizarCarrinho() {
    const lista = document.getElementById("listaDeItens");
    lista.innerHTML = "";

    if (items.length == 0) {
        document.getElementById("container").innerHTML = ''
        const vazio = document.createElement("h2")
        vazio.textContent = "Você ainda não colocou nenhum produto no carrinho!"

        document.getElementById("container").appendChild(vazio)
    }

    items.forEach(produto => {
        const conteudo = document.createElement('div')
        conteudo.classList.add('conteudo')

        const imgDiv = document.createElement('div')
        imgDiv.classList.add('imagem')

        const img = document.createElement('img')
        img.classList.add("imgProduto")
        img.src = produto.imgUrl
        img.alt = produto.name

        imgDiv.appendChild(img)

        const contentDiv = document.createElement("div")
        contentDiv.classList.add("content")

        const nome = document.createElement("p")
        nome.textContent = produto.name

        const label = document.createElement("label")
        label.textContent = "Quantidade: "
        label.htmlFor = "quantidade"

        const quantia = document.createElement("b")
        quantia.textContent = produto.quantity
        quantia.style.marginInline = "5px"

        const diminuir = document.createElement("input")
        diminuir.type = "button"
        diminuir.value = "-"
        diminuir.onclick = () => quantia.textContent = atualizarQuantidade(produto, -1)

        const aumentar = document.createElement("input")
        aumentar.type = "button"
        aumentar.value = "+"
        aumentar.onclick = () => quantia.textContent = atualizarQuantidade(produto, 1)

        const tamanho = document.createElement("p")
        tamanho.textContent = `Tamanho escolhido: ${produto.size}`

        const preco = document.createElement("p")
        preco.textContent = `R$ ${produto.price.toFixed(2).toString().replace(".", ",")}`

        const button = document.createElement("button")
        button.type = "button"
        button.classList.add("botao")
        button.textContent = "remover"
        button.onclick = () => {
            items = items.filter(item => item !== produto);
            localStorage.setItem('carrinho', JSON.stringify(items))
            renderizarCarrinho()
        }

        contentDiv.appendChild(nome)
        contentDiv.appendChild(label)
        contentDiv.appendChild(diminuir)
        contentDiv.appendChild(quantia)
        contentDiv.appendChild(aumentar)
        contentDiv.appendChild(tamanho)
        contentDiv.appendChild(preco)
        contentDiv.appendChild(button)

        conteudo.appendChild(imgDiv)
        conteudo.appendChild(contentDiv)

        lista.appendChild(conteudo)
    });
}

function confirmarCompra() {
    // Salvar os dados no LocalStorage
    localStorage.setItem("items", JSON.stringify(items));

    // Redirecionar para a outra página
    window.location.href = "../finalizarCompra/Finalizar.html";
}

function voltarHome() {
    location.href = "../home/home.html"
}

function logout() {
    localStorage.clear();
    window.location.href = "../index.html"
}

function atualizarQuantidade(produto, delta) {
    // Encontre o produto no carrinho
    const index = items.findIndex(item => item.id === produto.id && item.size === produto.size);

    if (index !== -1) {
        // Atualize a quantidade do produto
        items[index].quantity += delta;

        // Impede que a quantidade fique menor que 1
        if (items[index].quantity < 1) {
            items[index].quantity = 1;
        }

        // Atualize o localStorage
        localStorage.setItem("carrinho", JSON.stringify(items));

        // Atualize o texto da quantidade
        return items[index].quantity;
    }
}