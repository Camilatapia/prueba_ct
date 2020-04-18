"use strict";
// este array se carga de forma asincrona mediante Ajax
const endpoint = 'http://localhost:8080/apprestct/api/cursos/';
//const endpoint = 'http://127.0.0.1:5500/appclient/js/data/personas.json';
let cursos =[];



window.addEventListener('load', init() );

function init(){
console.debug('Document Load and Ready');
//listener();

//initGallery();
pintarCursos( cursos );

const promesa = ajax("GET", endpoint, undefined);
promesa
.then( data => {
        console.trace('promesa resolve'); 
        cursos = data;
        pintarCursos( cursos );

}).catch( error => {
        console.warn('promesa rejectada');
        alert(error);
});

console.debug('continua la ejecuion del script de forma sincrona');
       
    
} //init

function pintarCursos(arrayCursos){


        let lista=document.getElementById('cursos');
        lista.innerHTML ='';
        arrayCursos.forEach( (p,i) => lista.innerHTML += `
        <td class= "align-self-center">
        <img src="img/${p.imagen}" alt="imagen">
         </td>
         <td class= "align-self-center">
             ${p.id}
         </td>
         <td class= "align-self-center">
             ${p.titulo}
         </td>
         <td class= "align-self-center">
             ${p.precio}
         </td>`);

                                            


  
     }
    

/*function listener(){

let selectorSexo = document.getElementById('selectorSexo');
let inputNombre = document.getElementById('inombre');

selectorSexo.addEventListener('change', function(){
const sexo = selectorSexo.value;
console.debug('cambiado select ' + sexo);
        if ( 't' != sexo ){
            const personasFiltradas = personas.filter( el => el.sexo == sexo );
            console.info('el sexo es' + sexo);
            pintarLista(personasFiltradas);
        }else{
            pintarLista(personas);
        }    
      });

inputNombre.addEventListener('keyup', function(){

        const busqueda = inputNombre.value.toLowerCase();
        console.info('tecla pulsada, valor input ' +  busqueda );
        if ( busqueda ){            const personasFiltradas = personas.filter( el => el.nombre.toLowerCase().includes(busqueda));
         pintarLista(personasFiltradas);
        }else{
        pintarLista(personas);
         }    
    });
}*/


/*function eliminar(indice){
    let cursoSeleccionado = cursos[indice];
    console.debug('click eliminar persona %o', cursoSeleccionada);
    const mensaje = `¿Estas seguro que quieres eliminar  a ${cursoSeleccionado.titulo} ?`;
    if ( confirm(mensaje) ){

        const url = endpoint + personSeleccionada.id;
        ajax('DELETE', url, undefined)
        .then( data => {
                    // conseguir de nuevo todos los alumnos
                    ajax("GET", endpoint, undefined)               
                    .then( data => {
                            console.trace('promesa resolve'); 
                            personas = data;
                            pintarLista( personas );
                
                    }).catch( error => {
                            console.warn('promesa rejectada');
                            alert(error);
                    });

                    })
        .catch( error => {
                    console.warn('promesa rejectada');
                    alert(error);
                    });

        }

    }*/

/*function seleccionar(indice){

    let  cursoSeleccionado = { "id":0, "titulo": "sin titulo" , "imagen" : "imagen1.png", "seprecio": 0 };

    if ( indice > -1 ){
        cursoSeleccionado = cursos[indice];
    }
    
    console.debug('click guardar persona %o', cursoSeleccionado);
   
    //rellernar formulario
    /*document.getElementById('inputId').value     = personaSeleccionada.id;
    document.getElementById('inputNombre').value = personaSeleccionada.nombre;
    document.getElementById('guardarSexo').value = personaSeleccionada.sexo;
    document.getElementById('inputAvatar').value = personaSeleccionada.avatar;*/

    //seleccionar Avatar
    /*const imagenes = document.querySelectorAll('#gallery img');
    avatares.forEach( el => {
        el.classList.remove('selected');
        if ( "img/"+cursoSeleccionado.imagen == el.dataset.path ){
            el.classList.add('selected');
        }
    });

       

}*/

/*function guardar(){

    console.trace('click guardar');
    const id = document.getElementById('inputId').value;
    const nombre = document.getElementById('inputNombre').value;
    const sexo = document.getElementById('guardarSexo').value;
    const avatar = document.getElementById('inputAvatar').value;

    let persona = {
        "id" : id,
        "nombre" : nombre,
        "avatar" : avatar,
        "sexo" : sexo
    };

    console.debug('persona a guardar %o', persona);

    

       //Crear nueva persona
       if ( id == 0 ){ 
        console.trace('Crear nueva persona');
      
        ajax('POST',endpoint, persona)
            .then( data => {
 
                    // conseguir de nuevo todos los alumnos
                    ajax("GET", endpoint, undefined)               
                    .then( data => {
                            console.trace('promesa resolve'); 
                            personas = data;
                            //vaciar datos del formulario
                            
                            pintarLista(personas);
                
                    }).catch( error => {
                            console.warn('promesa rejectada');
                            alert(error);
                    });

            })
            .catch( error => {
                console.warn('promesa rejectada');
                alert(error);
            });
        

    // Modificar una persona
    }else{
        console.trace('Modificar persona');

        const url = endpoint + persona.id;
        ajax('PUT', url , persona)
            .then( data => {
 
                    // conseguir de nuevo todos los alumnos
                    ajax("GET", endpoint, undefined)               
                    .then( data => {
                            console.trace('promesa resolve'); 
                            personas = data;
                            pintarLista( personas );
                
                    }).catch( error => {
                            console.warn('promesa rejectada');
                            alert(error);
                    });

            })
            .catch( error => {
                console.warn('No se pudo actualizar');
                alert(error);
            });
        
    }

}*/




//Cargar avatares
/*function initGallery(){
    let divGallery =  document.getElementById('gallery');
    for ( let i = 1; i <= 7 ; i++){
        divGallery.innerHTML += `<img onclick="selectAvatar(event)" 
                                      class="avatar" 
                                      data-path="avatar${i}.png"
                                      src="img/avatar${i}.png">`;
    }
}

//Seleccionar avatar 
function selectAvatar(evento){
    console.trace('click avatar');
    const avatares = document.querySelectorAll('#gallery img');
    //eliminamos la clases 'selected' a todas las imagenes del div#gallery
    avatares.forEach( el => el.classList.remove('selected') );
    // ponemos clase 'selected' a la imagen que hemos hecho click ( evento.target )
    evento.target.classList.add('selected');

    let iAvatar = document.getElementById('inputAvatar');
    //@see: https://developer.mozilla.org/es/docs/Learn/HTML/como/Usando_atributos_de_datos
    iAvatar.value = evento.target.dataset.path;

}*/
