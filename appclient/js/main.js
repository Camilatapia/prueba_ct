"use strict";
// este array se carga de forma asincrona mediante Ajax
//const endpoint = 'http://localhost:8080/apprest/api/personas/';
const endpoint = 'http://127.0.0.1:5500/appclient/js/data/personas.json';
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
    for(let i=0; i < arrayPersonas.length; i++){
           const alumno = arrayPersonas[i];
            lista.innerHTML += `<li><img src="img/${alumno.avatar}" alt="avatar"> ${alumno.nombre}
            <div class="row justify-content-end"><i class="fas fa-pencil-ruler" onclick="seleccionar(${i})"></i>
            <i class="fas fa-trash" onclick="eliminar(${i})"></i></div> </li>`;
        }
    }
    

function listener(){

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
}


function eliminar(indice){
    let personaSeleccionada = personas[indice];
    console.debug('click eliminar persona %o', personaSeleccionada);
    const mensaje = `¿Estas seguro que quieres eliminar  a ${personaSeleccionada.nombre} ?`;
    if ( confirm(mensaje) ){

        //TODO mirar como remover de una posicion
        //personas = personas.splice(indice,1);
        personas = personas.filter( el => el.id != personaSeleccionada.id) 
        pintarLista(personas);
        //TODO llamada al servicio rest

    }

}
function seleccionar(indice){

   // let  personaSeleccionada = { "id": "0","nombre": "ain " };

    //if ( indice != 0 ){
    let personaSeleccionada = personas[indice];
    //}
    
    console.debug('click guardar persona %o', personaSeleccionada);
   
    //rellernar formulario
    document.getElementById('inputId').value = personaSeleccionada.id;
    document.getElementById('inputNombre').value = personaSeleccionada.nombre;
    document.getElementById('guardarSexo').value = personaSeleccionada.sexo;

}

function guardar(){

    //console.trace('click guardar');
    let id = document.getElementById('inputId').value;
    let nombre = document.getElementById('inputNombre').value;
    let sexo = document.getElementById('guardarSexo').value;

    let persona = {
        "id" : id,
        "nombre" : nombre,
        "avatar" : "avatar7.png",
        "sexo"   : sexo
    };

    console.debug('persona a guardar %o', persona);
    personas.push(persona);
    
    //TODO llamar servicio rest
   
    localStorage.setItem('rutina', JSON.stringify(personas));
    
    pintarLista(personas);

}
function busqueda( sexo = 't', nombreBuscar = '' ){

    console.info('Busqueda sexo %o nombre %o', sexo, nombreBuscar );
}

function modificar(){
    console.trace('click modificar');
    let id = document.getElementById('inputId').value;
    let nombre = document.getElementById('inputNombre').value;
    let sexo = document.getElementById('guardarSexo').value;

    

    let datos=new FormData();
    datos.append(inputId, id);
    datos.append(inputNombre, nombre);
    datos.append(guardarSexo, sexo);
    
    var param= {
        method: 'post',
        body: datos
    }




    
}
