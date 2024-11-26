// INICIA ANTES DE TUDO
checkAuthUserId()
// INICIA ANTES DE TUDO

var userId
var products
function checkAuthUserId() {
    userId = localStorage.getItem('authUserId');
    console.log(userId)

    if (!userId) {
        alert("Usuário não autenticado. Redirecionando para a página de login.")
        window.location.href = "../../index.html";
    } else {
        console.log("userId carregado com sucesso")
    }
}

function confirmarCompra() {
    // Salvar os dados no LocalStorage
    localStorage.setItem("items", JSON.stringify(items));

    // Redirecionar para a outra página
    window.location.href = "../finalizarCompra/Finalizar.html";
}