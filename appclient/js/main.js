"use strict";
// este array se carga de forma asincrona mediante Ajax
const endpoint = 'http://localhost:8080/apprestct/api/';
let personas =[];
let cursos=[];
let personaSeleccionada = { "id":0, 
                        "nombre": "sin nombre" , 
                        "avatar" : "img/avatar7.png", 
                        "sexo": "h",
                        "cursos": []
                        };

window.addEventListener('load', init() );

function init(){
console.debug('Document Load and Ready');
listener();
initGallery();
pintarLista( personas );

//Al abrir ventana modal
pintarCursos();

const url = endpoint + 'personas/';
const promesa = ajax("GET", url, undefined);
promesa
.then( data => {
        console.trace('promesa resolve'); 
        personas = data;
        pintarLista( personas );

}).catch( error => {
        console.warn('promesa rejectada');
        alert(error);
});

console.debug('continua la ejecucion del script de forma sincrona');
       
} //init

/**
 * 
 * Se ejecuta al recargar la pagina
 * Muestra el listado de las personas 
 * @param {*} id  array de personas[]
 */
function pintarLista(arrayPersonas){

    let lista=document.getElementById('alumnos');
    lista.innerHTML ='';
    arrayPersonas.forEach( (p,i) => lista.innerHTML += 
    `<li>
        <img src="img/${p.avatar}" alt="avatar">${p.nombre}
        <div class="row justify-content-end">
            <span class="fright" >${p.cursos.length} cursos</span>
            <i class="fas fa-pencil-ruler" onclick="seleccionar(${p.id})"></i>
            <i class="fas fa-trash" onclick="eliminar(${p.id})"></i>
        </div> 
    </li>`);
}//pintarLista

/**
 * 
 * Se ejecuta en tres casos
 * 1.Buscar persona por sexo o ingresando un nombre
 * 2.Cuando el usuario usa el buscador de cursos
 * 3.Cuando el usuario hace click en boton "Anadir curso" se abre ventana modal
 * @param {*} sexo de personas, nombre de personas, titulo de curso, click boton de modal
 */
function listener(){
    //1) buscar persona por sexo o nombre
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
    });//buscador por sexo

    inputNombre.addEventListener('keyup', function(){
        const busqueda = inputNombre.value.toLowerCase();
        console.info('tecla pulsada, valor input ' +  busqueda );
            if ( busqueda ){ 
                const personasFiltradas = personas.filter( el => el.nombre.toLowerCase().includes(busqueda));
                pintarLista(personasFiltradas);
            }else{
                pintarLista(personas);
            }    
    });//buscador por nombre de persona

    // 2) filtro de cursos
    let filtroCursos = document.getElementById('filtroCurso');
    filtroCursos.addEventListener('keyup',  function(event) {
        let filtroValor = filtroCursos.value.trim();        
            if ( filtroValor.length >= 3 ){
                console.debug('filtroCursos keyup ' + filtroValor );
                pintarCursos(filtroValor);
            }else{
                pintarCursos();
            }
    });//buscar por nombre de curso

    //3) Modal  
    var modal = document.getElementById("modal");
    var btn = document.getElementById("btnModal");    
    var spanClose = document.getElementById("close");

    // cuando el usuario hace click en boton se abre ventana modal 
    btn.onclick = () =>  {
        pintarCursos();
        modal.style.display = "block";
        modal.classList.add('animated','zoomIn');
    }

    // cuando el usuario hace click en (x) se cierra ventana modal
    spanClose.onclick = () => {
        modal.style.display = "none";        
    }    
        
    // cuando el usuario hace click fuera de ventana modal se cierra 
    window.onclick = (event) => {
        if (event.target == modal) {
            modal.style.display = "none";            
        }
    }
}//listener

/**
 * 
 * Se ejecuta al pulsar el boton con forma de papelera  
 * Elimina al alumno de la base de datos
 * @param {*} id  id del alumno
 * @see personaSeleccionada el id seleccionado para eliminar
 */
function eliminar(id=0){
    personaSeleccionada = personas.find( el => el.id == id);
    console.debug('click eliminar persona %o', personaSeleccionada);
    const mensaje = `¿Estas seguro que quieres eliminar  a ${personaSeleccionada.nombre} ?`;
    if ( confirm(mensaje) ){
        const url = endpoint + 'personas/' + personaSeleccionada.id;
        ajax('DELETE', url, undefined)
        .then( data => {
                    // conseguir de nuevo todos los alumnos
                    const urlPersonas = endpoint + 'personas/';
                    ajax("GET", urlPersonas, undefined)               
                    .then( data => {
                            console.trace('promesa resolve'); 
                            personas = data;
                            pintarLista( personas );
                
                    }).catch( error => {
                            console.warn('promesa rejectada %o' , error);
                            alert(error.informacion);
                    });

                })
        .catch( error => {
                    console.warn('promesa rejectada');
                    alert(error);
                    });
        }
    }//eliminar

    /**
 * 
 * Se ejecuta al pulsar el boton de editar(al lado de la papelera) o boton 'Nueva Persona' 
 * Rellena el formulario con los datos de la persona
 * @param {*} id  id del alumno, si no existe en el array usa personaSeleccionada
 * @see personaSeleccionada = { "id":0, "nombre": "sin nombre" , "avatar" : "img/avatar7.png", "sexo": "h" };
 */
function seleccionar(id=0){

    let cntFormulario = document.getElementById('content-formulario');
    cntFormulario.style.display = 'block';
    cntFormulario.classList.add('animated','fadeInRight');

    // para buscar por indice usar find
    personaSeleccionada = personas.find( el=> el.id == id);
    if ( !personaSeleccionada ){
        personaSeleccionada = { "id":0, 
                                "nombre": "sin nombre" ,
                                "avatar" : "avatar7.png",
                                "sexo": "h",
                                "cursos": [] };
    }
   
    
    console.debug('click guardar persona %o', personaSeleccionada);
   
    //rellernar formulario
    document.getElementById('inputId').value     = personaSeleccionada.id;
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

    // pintar cursos del alumno
    let listaCursosAlumno = document.getElementById('cursosAlumno');
    listaCursosAlumno.innerHTML = '';
    personaSeleccionada.cursos.forEach( el => {
            listaCursosAlumno.innerHTML += `<li>
                                            ${el.titulo}
                                            <i class="fas fa-trash" onclick="eliminarCurso(event, ${personaSeleccionada.id},${el.id})"></i>
                                            </li>`;

                                        });
    
        }//seleccionar

/**
 * 
 * Se ejecuta al pulsar el boton de "Guardar"  
 * Inserta datos de nueva persona o modifica de persona que existe
 * @param {*} id  id de la persona, si no existe la persona se genera un nuevo id automaticamente
 * @see persona = { "id":id, "nombre": nombre , "avatar" : avatar, "sexo": sexo };
 */  
function guardar(){

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
        const urlPersonas = endpoint + 'personas/';      
        ajax('POST',urlPersonas, persona)
            .then( data => {

                alert( persona.nombre + ' ya esta con nosotros ');
                //limpiar formulario
                document.getElementById('inputId').value = 0;
                document.getElementById('inputNombre').value = '';               
                document.getElementById('inputAvatar').value = 'img/avatar1.png';
                document.getElementById('guardarSexo').value = 't';
                
                    // conseguir de nuevo todos los alumnos
                    ajax("GET", urlPersonas, undefined)               
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
                alert(error.informacion);
            });//Fin crear persona nueva
   // Modificar una persona
    }else{
        console.trace('Modificar persona');
        const url = endpoint + 'personas/' + persona.id;
        ajax('PUT', url , persona)
            .then( data => {
 
                    // conseguir de nuevo todos los alumnos
                    const urlPersonas = endpoint + 'personas/';
                    ajax("GET", urlPersonas, undefined)               
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
                alert(error.informacion);
            });
        
    }//Fin modificar persona

}//Fin función guardar


/**
 * 
 * Se ejecuta al pulsar el boton de editar(al lado de la papelera) o boton 'Nueva Persona' 
 * Rellena el formulario con los datos de la persona
 * @param {*} id  id del alumno, si no existe en el array usa personaSeleccionada
 * @see personaSeleccionada = { "id":0, "nombre": "sin nombre" , "avatar" : "img/avatar7.png", "sexo": "h" };
function busqueda( sexo = 't', nombreBuscar = '' ){

    console.info('Busqueda sexo %o nombre %o', sexo, nombreBuscar );
}
 */

 /**
 * 
 * Se ejecuta al recargar la pagina 
 * Muestra los avatares de la galeria de imagenes
  */
//Cargar avatares
function initGallery(){
    let divGallery =  document.getElementById('gallery');
    for ( let i = 1; i <= 7 ; i++){
        divGallery.innerHTML += `<img onclick="selectAvatar(event)" 
                                      class="avatar" 
                                      data-path="avatar${i}.png"
                                      src="img/avatar${i}.png">`;
    }
}//initGallery: cargar avatares

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

}//selectAvatar


 /**
 * 
 * Se ejecuta al pulsar el boton "Anadir curso" 
 * Muestra la lista de todos los cursos disponibles
 * @param {*} filtro filtro en el buscador, si es vacio muestra todos los cursos, sino filtra la lista segun param
 */
function pintarCursos( filtro = '' ){
    console.trace('cargar cursos');   
    const urlCursos = endpoint + 'cursos/?filtro='  + filtro;
       ajax( 'GET', urlCursos, undefined )
        .then( data => {
             cursos = data;
             // cargar cursos en lista
            let lista=document.getElementById('cursos');
            lista.innerHTML ='';
            cursos.forEach( el => lista.innerHTML += `
                <td class= "align-self-center">
                    <img src="img/${el.imagen}" alt="imagen">
                </td>
                <td class= "align-self-center">
                    ${el.titulo}
                </td>
                <td class= "align-self-center">
                    <span>${el.precio} €</span>    
                </td>
                <td class= "align-self-center" onclick="asignarCurso( 0, ${el.id})" >
                    [x] Asignar
                </td>`);
            seleccionar(personaSeleccionada.id);   
            })
        .catch( error => alert('No se pueden cargar cursos' + error));

   }//pintarCursos

 
   /**
 * Se ejecuta al pulsar la papelera (al lado del listado de cursos comprados) es necesario seleccionar una persona antes
 * Elimina el curso asignado a esa persona del array cursos[]. 
 * @param {*} idPersona 
 * @param {*} idCurso 
 */
function eliminarCurso( event, idPersona, idCurso ){

    console.debug(`click eliminarCurso idPersona=${idPersona} idCurso=${idCurso}`);

    const url = endpoint + 'personas/' + idPersona + "/curso/" + idCurso;
    ajax('DELETE', url, undefined)
        .then( data => {
            alert('Curso Eliminado');
            //llamada ajax para actualizar cursos.length de cada alumno
            const urlPersonas = endpoint + 'personas/';
            const promesa = ajax("GET", urlPersonas, undefined);
            promesa
                .then( data => {
                    console.trace('promesa resolve'); 
                    personas = data;
                    pintarLista( personas );
            
                }).catch( error => {
                    console.warn('promesa rejectada');
                    alert(error);
                });
            event.target.parentElement.classList.add('animated', 'bounceOut');
        })
        .catch( error => alert(error));
}//eliminarCurso


/**
 * Se ejecuta al pulsar el boton "Asignar" de la ventana modal
 * Inserta un curso al array cursos[] de la persona seleccionada
 * @param {*} idPersona 
 * @param {*} idCurso 
 */
function asignarCurso( idPersona = 0, idCurso ){

    idPersona = (idPersona != 0) ? idPersona : personaSeleccionada.id;
    console.debug(`click asignarCurso idPersona=${idPersona} idCurso=${idCurso}`);

    const url = endpoint + 'personas/' + idPersona + "/curso/" + idCurso;
    ajax('POST', url, undefined)
        .then( data => {
            alert('Curso Asignado');

            // cerrar modal
            document.getElementById("modal").style.display = 'none';

            const curso = data.data;
            // pintar curso al final de la lista        
            let lista = document.getElementById('cursosAlumno');   
            lista.innerHTML +=    
                            `<li class="animated bounceIn">
                                ${curso.titulo}
                            <i class="fas fa-trash" onclick="eliminarCurso(event, ${idPersona},${curso.id})"></i>    
                            </li>`;
       
            //llamada ajax para actualizar cursos.length de cada alumno
            const urlPersonas = endpoint + 'personas/';
            const promesa = ajax("GET", urlPersonas, undefined);
            promesa
                .then( data => {
                    console.trace('promesa resolve'); 
                    personas = data;
                    pintarLista( personas );
                })
                .catch( error => {
                    console.warn('promesa rejectada');
                    alert(error);
                });
        })
        .catch( error => {
            console.warn('promesa rejectada');
            alert(error.informacion);
        });
}//asignarCurso


    

