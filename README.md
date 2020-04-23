# prueba_ct
Dos proyectos AppCliente ( HTML5, CSS3, JS ) y AppRest ( rest api con Java JAX-RS )

![Listado alumnos](https://github.com/Camilatapia/prueba_ct/blob/master/version2.png)

## AppClient
1. Descripción:

    AppCliente es una API que representa el diseño gráfico y formato de nuestra aplicación web para gestionar la información de los alumnos. Mediante la identificación de la URI y Javascript se puede controlar el comportamiento de los diferentes elementos, aportando funacionalidad y dinamismo a cada elemento.

    La aplicación muestra el listado de alumnos permitiendo buscar un alumno por su sexo o nombre; En el botón "Nueva persona" se puede ingresar datos de un nuevo alumno, al seleccionar cada alumno se pueden visualizar sus datos y los cursos que ha comprado, los cuales pueden ser modificados.

    Para visualizar esta descripción haz click [aquí](https://github.com/Camilatapia/prueba_ct/blob/master/version2.png)

    Si haces click en "Añadir curso" se abre una ventana modal que permite asignar otro curso al alumno. Para ver haz click [aquí](https://github.com/Camilatapia/prueba_ct/blob/master/version%202-asignar.png)

2. Tecnología utilizada: Html5, CSS3, Javascript 

3. Configuración: 
    - Para cambiar estilos debe seguir la siguiente ruta: css/style.css
    - Modificar la url base de la api, llamada endpoint debe ir al inicio de main.js en carpeta "js". 
    - Dentro del main.js se definen diferentes url que recibe funcion ajax(), que se encuentra definida en ajax.js en carpeta "js"


## AppRest:
1. Descripción:
AppRest es una API REST que permite gestionar los datos de los alumnos y los cursos asociados a cada alumno. La API representa una interfaz uniforme que sistematiza el proceso con la información, mediante la identificación de una URI se puede realizar la transferencia de datos de cada alumno y acciones concretas del CRUD (POST, GET, PUT y DELETE).

2. Tecnología usada: Java JAX-RS

3. Configuración ( conexión a bbdd y logs ) 
    - Para configurar base de datos "alumnos" modificar context.xml
        - ruta: apprest\WebContent\META-INF\context.xml 
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
    - ajax('DELETE', 'http://localhost:8080/apprestct/api/personas/' + idPersona + "/curso/" + idCurso, undefined): Eliminar el curso idCurso asociado a alumno con idPersona
    - ajax('POST', 'http://localhost:8080/apprestct/api/personas/' + idPersona + "/curso/" + idCurso, undefined): Insertar un curso idCurso al alumno idPersona

## Tags o Versiones:  
### Version V.1.0: 
API para gestionar información de alumnos: Buscar alumno, modificar sus datos, crear alumnos nuevos o eliminar
### Version V.2.0:
API para gestionar información de alumnos y sus cursos asociados. Mantiene operaciones de versión anterior más la asignación o eliminación de cursos comprados por cada alumno.


