var items = {
    items: [
        {
            "idProduct": 1,
            "quantity": 10
        },
        {
            "idProduct": 2,
            "quantity": 5
        },
        {
            "idProduct": 3,
            "quantity": 15
        }
    ]
}

function confirmarCompra() {
    // Salvar os dados no LocalStorage
    localStorage.setItem("items", JSON.stringify(items));

    // Redirecionar para a outra página
    window.location.href = "../finalizarCompra/Finalizar.html";
}