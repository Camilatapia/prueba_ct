# prueba_ct
Dos proyectos AppClient ( HTML5, CSS3, JS ) y AppRest ( Rest api con Java JAX-RS )


![Listado alumnos](https://github.com/Camilatapia/prueba_ct/blob/v3.0/appclient/img/Alumnos.png)

![Listado profesores](https://github.com/Camilatapia/prueba_ct/blob/v3.0/appclient/img/Profesores.png)

## AppClient
1. Descripción:

    AppCliente es una API que representa el diseño gráfico y formato de nuestra aplicación web para gestionar la información de los alumnos y profesores. Mediante la identificación de la URI y Javascript se puede controlar el comportamiento de los diferentes elementos, aportando funcionalidad y dinamismo a cada elemento.

    La aplicación muestra la "Sesión de alumnos" permitiendo buscar un alumno por su sexo o nombre; En el botón "Añadir Alumno" se puede ingresar datos de un nuevo alumno, al seleccionar cada alumno se pueden visualizar sus datos y los cursos que ha comprado, datos que pueden ser modificados por el administrador. El listado de curso muestra el profesor encargado de compartir cada curso.

    Para visualizar esta descripción haz click [aquí](https://github.com/Camilatapia/prueba_ct/blob/v3.0/appclient/img/Alumnos.png)

    Si haces click en "Añadir curso" se abre una ventana modal que permite asignar otro curso al alumno. Para ver más click [aquí](https://github.com/Camilatapia/prueba_ct/blob/v3.0/appclient/img/Cursos.png)

 La aplicación también muestra "Sesión de profesores" permitiendo buscar un profesor y gestionar sus datos, dar de alta a un nuevo profesor, asignar y/o eliminar los cursos que imparte. Para visualizar esta descripción haz click [aquí](https://github.com/Camilatapia/prueba_ct/blob/v3.0/appclient/img/Profesores.png)

2. Tecnología utilizada: Html5, CSS3, Javascript 

3. Configuración: 
    - Cambiar estilos propios en archivo [style.js](https://github.com/Camilatapia/prueba_ct/blob/master/appclient/css/style.css)
    - Sesión Profesores: Modificar la url base de la api en el inicio de [main2.js](https://github.com/Camilatapia/prueba_ct/blob/master/appclient/js/main2.js), llamada endpoint
    - Sesión Alumnos: Modificar la url base de la api en el inicio de [alumnos.js](https://github.com/Camilatapia/prueba_ct/blob/master/appclient/js/alumnos.js), llamada endpoint
    - En los archivos js se definen las diferentes url que recibe funcion [ajax()](https://github.com/Camilatapia/prueba_ct/blob/master/appclient/js/ajax.js) para realizar metodos del CRUD. 
    - La carpeta [img](https://github.com/Camilatapia/prueba_ct/tree/master/appclient/img) contiene todas las imagenes de perfil de alumnos e iconos de cada curso.


## AppRest:
1. Descripción:
AppRest es una API REST que permite gestionar la sesión de alumnos, sus datos y los cursos que han comprado, y la sesión de profesores, sus datos y los cursos que imparte. La API representa una interfaz uniforme que sistematiza el proceso con la información, es necesaria la identificación de una URI para realizar la transferencia de datos y ejecutar acciones del CRUD (POST, GET, PUT y DELETE).

2. Tecnología usada: Java JAX-RS

3. Configuración 
    - Para configurar base de datos ["alumnos"](https://github.com/Camilatapia/prueba_ct/blob/master/script-db.sql) modificar [context.xml](https://github.com/Camilatapia/prueba_ct/blob/master/apprest/WebContent/META-INF/context.xml)
        
~~~
username="root"
password="root"
~~~

4. Detalle API rest con llamadas ajax
    - ajax('POST','http://localhost:8080/apprestct/api/personas/', persona): Insertar un alumno nuevo en la base de datos
    - ajax("GET", 'http://localhost:8080/apprestct/api/personas/', undefined) : Obtener todos los alumnos 
    - ajax('PUT', 'http://localhost:8080/apprestct/api/personas/' + persona.id, persona) : Modificar datos de un alumno
    - ajax('DELETE', 'http://localhost:8080/apprestct/api/personas/' + personaSeleccionada.id, undefined): Eliminar un alumno de la base de datos
    - ajax( 'GET', 'http://localhost:8080/apprestct/api/cursos/?filtro='  + filtro, undefined ): Obtener listado de todos los cursos
    - ajax('DELETE', 'http://localhost:8080/apprestct/api/personas/' + idPersona + "/curso/" + idCurso, undefined): Eliminar el curso idCurso asociado a un alumno idPersona
    - ajax('POST', 'http://localhost:8080/apprestct/api/personas/' + idPersona + "/curso/" + idCurso, undefined): Insertar un curso idCurso a un alumno idPersona

## Tags o Versiones:  
### Version V.1.0: 
API para gestionar información de alumnos: Buscar alumno, modificar sus datos, crear alumnos nuevos o eliminar
### Version V.2.0:
API para gestionar información de alumnos y sus cursos asociados. Mantiene operaciones de versión anterior y se suma asignación o eliminación de cursos comprados por cada alumno.
### Versión V.3.0:
API para gestionar información de alumnos, profesores y sus cursos asociados respectivamente. Mantiene operaciones de versión anterior y se suma rol de profesor encargado de impartir el curso.

