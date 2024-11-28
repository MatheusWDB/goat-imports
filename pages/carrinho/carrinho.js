// INICIA ANTES DE TUDO
const userId = localStorage.getItem('authUserId');
var items = localStorage.getItem('carrinho') === null ?
    [] :
    JSON.parse(localStorage.getItem('carrinho'))
console.log(items)
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

    if (!lista) {
        console.error("Elemento #listaDeItens não encontrado no DOM.");
        return;
    }

    items.forEach(produto => {
        const conteudo = document.createElement('div')
        conteudo.classList.add('conteudo')

        const imgDiv = document.createElement('div')
        imgDiv.classList.add('imagem')

        const img = document.createElement('img')
        img.src = produto.imgUrl
        img.alt = produto.name

        imgDiv.appendChild(img)

        const contentDiv = document.createElement("div")
        contentDiv.classList.add("content")

        const nome = document.createElement("p")
        nome.textContent = produto.name

        const label = document.createElement("label")
        label.htmlFor = "quantidade"

        const input = document.createElement("input")
        input.type = "number"
        input.classList.add("quantidade")
        input.name = "quantidade"
        input.value = produto.quantity

        const tamanho = document.createElement("p")
        tamanho.textContent = `Tamanho escolhido: ${produto.size}`

        const preco = document.createElement("p")
        preco.textContent = `R$ ${produto.price}`

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
        contentDiv.appendChild(input)
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
    window.location.href = "../../index.html"
}