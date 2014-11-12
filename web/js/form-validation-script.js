var Script = function () {

    $.validator.setDefaults({
        submitHandler: function() { alert("submitted!"); }
    });

    $().ready(function() {
        // validate the comment form when it is submitted
//        $("#commentForm").validate();

        // validate signup form on keyup and submit
        $("#NuevoUsuario").validate({
            rules: {
                usuario: {
                    required: true,
                    minlength: 4
                },
                clave: {
                    required: true,
                    minlength: 5
                },
                rclave: {
                    required: true,
                    minlength: 5,
                    equalTo: "#clave"
                },
                mail: {
                    required: true,
                    email: true
                },
                tipo: "required"
            },
            messages: {
                usuario: {
                    required: "Por favor ingrese un usuario",
                    minlength: "Su usuario debe consisitir al menos de 4 caracteres"
                },
                clave: {
                    required: "Por favor ingrese una clave",
                    minlength: "Su clave debe ser de al menos 5 caracteres"
                },
                rclave: {
                    required: "Por favor ingrese una clave",
                    minlength: "Su clave debe ser de al menos 5 caracteres",
                    equalTo: "La clave no coincide"
                },
                email: "Por favor ingrese una dirección de correo válida",
                tipo: "Por favor seleccione un tipo"
            }
        });

    });


}();