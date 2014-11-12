/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var Script = function() {
    obtenerConfActual();
}();

function obtenerConfActual(){
    var url = "BDCtrl?op=obtener";
    var ai = new AJAXInteraction(url, cargarConfActual, "XML");
    ai.doGet();
}

function cargarConfActual(resultado) {
    var xmlresult = resultado.getElementsByTagName('Configuracion');
}