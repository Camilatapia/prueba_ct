"use strict";
// este array se carga de forma asincrona mediante Ajax
const endpoint = 'http://localhost:8080/apprestct/api/noticias/';

let noticias =[];

window.addEventListener('load', init() );

function init(){
console.debug('Document Load and Ready');

pintarNoticias(noticias);

const promesa = ajax("GET", endpoint, undefined);
promesa
.then( data => {
        console.trace('promesa resolve'); 
        noticias = data;
        pintarNoticias( noticias );

}).catch( error => {
        console.warn('promesa rejectada');
        alert(error);
});

console.debug('continua la ejecuion del script de forma sincrona');
       
    
} //init

function pintarNoticias(arrayNoticias){


        let lista=document.getElementById('noticias');
        lista.innerHTML ='';
        arrayNoticias.forEach( (p,i) => lista.innerHTML += `
        <td class= "align-self-center">
            ${p.id}
         </td>
         <td class= "align-self-center">
            ${p.fecha}
         </td>
         <td class= "align-self-center">
             ${p.titulo}
         </td>
         <td class= "align-self-center">
             ${p.contenido}
         </td>`);
 
     }
    
   

