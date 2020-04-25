# prueba_ct
Dos proyectos AppClient ( HTML5, CSS3, JS ) y AppRest ( Rest api con Java JAX-RS )

![Listado alumnos](https://github.com/Camilatapia/prueba_ct/blob/master/version2.png)

## AppClient
1. Descripción:

    AppCliente es una API que representa el diseño gráfico y formato de nuestra aplicación web para gestionar la información de los alumnos. Mediante la identificación de la URI y Javascript se puede controlar el comportamiento de los diferentes elementos, aportando funacionalidad y dinamismo a cada elemento.

    La aplicación muestra el listado de alumnos permitiendo buscar un alumno por su sexo o nombre; En el botón "Nueva persona" se puede ingresar datos de un nuevo alumno, al seleccionar cada alumno se pueden visualizar sus datos y los cursos que ha comprado, los cuales pueden ser modificados.

    Para visualizar esta descripción haz click [aquí](https://github.com/Camilatapia/prueba_ct/blob/master/version2.png)

    Si haces click en "Añadir curso" se abre una ventana modal que permite asignar otro curso al alumno. Para ver más click [aquí](https://github.com/Camilatapia/prueba_ct/blob/master/version%202-asignar.png)

2. Tecnología utilizada: Html5, CSS3, Javascript 

3. Configuración: 
    - Cambiar estilos propios en archivo [style.js](https://github.com/Camilatapia/prueba_ct/blob/master/appclient/css/style.css)
    - Modificar la url base de la api en el inicio de [main.js](https://github.com/Camilatapia/prueba_ct/blob/master/appclient/js/main.js), llamada endpoint
    - En main.js se definen las diferentes url que recibe funcion [ajax()](https://github.com/Camilatapia/prueba_ct/blob/master/appclient/js/ajax.js) para realizar metodos del CRUD. 
    - La carpeta [img](https://github.com/Camilatapia/prueba_ct/tree/master/appclient/img) contiene todas las imagenes de perfil de alumnos e iconos de cada curso.


## AppRest:
1. Descripción:
AppRest es una API REST que permite gestionar los datos de los alumnos y los cursos que han comprado, para mayor detalle del modelo de los datos ver el [diagrama de entidad relación] (https://github.com/Camilatapia/prueba_ct/blob/master/diagrama.png). La API representa una interfaz uniforme que sistematiza el proceso con la información, es necesaria la identificación de una URI para realizar la transferencia de datos y ejecutar acciones del CRUD (POST, GET, PUT y DELETE).

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


