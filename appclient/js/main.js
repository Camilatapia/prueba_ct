"use strict";
// este array se carga de forma asincrona mediante Ajax
const endpoint = 'http://localhost:8080/apprestct/api/personas/';
//const endpoint = 'http://127.0.0.1:5500/appclient/js/data/personas.json';
let personas =[];

let titulos = document.getElementsByTagName('h1');
for(let i=0; i < titulos.length ; i++ ){
        let p = titulos[i];
        p.style.color = 'black';
        
    }

window.addEventListener('load', init() );

function init(){
console.debug('Document Load and Ready');
listener();

initGallery();

const promesa = ajax("GET", endpoint, undefined);
promesa
.then( data => {
        console.trace('promesa resolve'); 
        personas = data;
        pintarLista( personas );

}).catch( error => {
        console.warn('promesa rejectada');
        alert(error);
});

console.debug('continua la ejecuion del script de forma sincrona');
// CUIDADO!!!, es asincrono aqui personas estaria sin datos
// pintarLista( personas );
       
    
} //init

function pintarLista(arrayPersonas){
    let lista=document.getElementById('alumnos');
    lista.innerHTML ='';
    arrayPersonas.forEach( (p,i) => lista.innerHTML += `<li>
    <img src="img/${p.avatar}" alt="avatar">${p.nombre}
    <div class="row justify-content-end"><i class="fas fa-pencil-ruler" onclick="seleccionar(${i})"></i>
            <i class="fas fa-trash" onclick="eliminar(${i})"></i></div> 
 </li>` );
  /*  for(let i=0; i < arrayPersonas.length; i++){
           const alumno = arrayPersonas[i];
            lista.innerHTML += `<li><img src="img/${alumno.avatar}" alt="avatar"> ${alumno.nombre}
            <div class="row justify-content-end"><i class="fas fa-pencil-ruler" onclick="seleccionar(${i})"></i>
            <i class="fas fa-trash" onclick="eliminar(${i})"></i></div> </li>`;
        }*/
    }
    

function listener(){

let selectorSexo = document.getElementById('selectorSexo');
let inputNombre = document.getElementById('inombre');


//selectorSexo.addEventListener('change', busqueda( selectorSexo.value, inputNombre.value ) );

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
}


function eliminar(indice){
    let personaSeleccionada = personas[indice];
    console.debug('click eliminar persona %o', personaSeleccionada);
    const mensaje = `¿Estas seguro que quieres eliminar  a ${personaSeleccionada.nombre} ?`;
    if ( confirm(mensaje) ){

        const url = endpoint + personaSeleccionada.id;
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


    }


function seleccionar(indice){

    let  personaSeleccionada = { "id":0, "nombre": "sin nombre" , "avatar" : "avatar7.png", "sexo": "h" };

    if ( indice > -1 ){
        personaSeleccionada = personas[indice];
    }
    
    console.debug('click guardar persona %o', personaSeleccionada);
   
    //rellernar formulario
    document.getElementById('inputId').value = personaSeleccionada.id;
    document.getElementById('inputNombre').value = personaSeleccionada.nombre;
    document.getElementById('guardarSexo').value = personaSeleccionada.sexo;

    document.getElementById('inputAvatar').value = personaSeleccionada.avatar;

    //seleccionar Avatar
    const avatares = document.querySelectorAll('#gallery img');
    avatares.forEach( el => {
        el.classList.remove('selected');
        if ( "img/"+personaSeleccionada.avatar == el.dataset.path ){
            el.classList.add('selected');
        }
    });

       /*
    let select = document.getElementById('inputSexo');
    const sexo = personaSeleccionada.sexo;
    switch( sexo ){
        case "h":
            select.item(1).selected = "selected";
            break;
        case "m":
            select.item(2).selected = "selected";
            break;
        default:
            select.item(0).selected = "selected";
    }
    */

}

function guardar(){

    //console.trace('click guardar');
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

    //TODO llamar servicio rest

       //CREAR
       if ( id == 0 ){ 
        console.trace('Crear nueva persona');
        //persona.id = ++personas.length;
        //personas.push(persona);

        ajax('POST',endpoint, persona)
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
        

    // MODIFICAR
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

}


function busqueda( sexo = 't', nombreBuscar = '' ){

    console.info('Busqueda sexo %o nombre %o', sexo, nombreBuscar );
}


/**
 * Carga todas las imagen de los avatares
 */
function initGallery(){
    let divGallery =  document.getElementById('gallery');
    for ( let i = 1; i <= 7 ; i++){
        divGallery.innerHTML += `<img onclick="selectAvatar(event)" 
                                      class="avatar" 
                                      data-path="avatar${i}.png"
                                      src="img/avatar${i}.png">`;
    }
}

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

}



    

