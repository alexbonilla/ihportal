var Script = function() {
    obtenerUsuario();
}();

function obtenerUsuario() {
    var url = "CuentaCtrl?op=nombre_cliente";
    var ai = new AJAXInteraction(url, cargarUsuario, "Text");
    ai.doGet();
}

function cargarUsuario(resultado) {
    document.getElementsByClassName("username")[0].innerHTML = resultado;
}

