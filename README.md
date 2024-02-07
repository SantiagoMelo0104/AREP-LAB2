# LABORATORIO 2 AREP -  DISEÑO Y ESTRUCTURACIÓN DE APLICACIONES DISTRIBUIDAS EN INTERNET


En este laboratorio, vamos a construir un servidor web simple que soporte múltiples solicitudes seguidas (no concurrentes). Para ello, utilizaremos Java y las librerías para manejo de la red. El servidor leerá los archivos del disco local y retornará todos los archivos solicitados, incluyendo páginas html, archivos java script, css e imágenes.

# Iniciando 
A continuación se indican una serie de instruciones para bajar y ejecutar el proyecto de manera exitosa:

Es **importante**❗tener instalado: 
- [MAVEN](https://maven.apache.org) : Manejo de las dependecias. 
- [GIT](https://git-scm.com) : Control de versiones.
- [JAVA](https://www.java.com/es/) : Lenguaje de programación (JDK 20). 

# Instalación ⬇️
Los siguiente comando le permitira clonar el repositorio de manera local:
~~~
git clone https://github.com/SantiagoMelo0104/AREP-LAB2.git
~~~

# Ejecución🪄 
Para este ejemplo usaremos el IDE de Intelij:

+ Una vez clonado, abrimos el proyecto en en IDE y ubicamos la siguiente clase **HttpServer**.
  
![image]()
+ Para ejecutar el proyecto podemos hacerlo presionando cualquiera de los recuadros a continuación
  
![image]()
+ A continuación dirijase al navegador de su preferencia y vaya a la siguiente dirección  ```http://localhost:35000/la_carpeta _del_tipo/el_archivo_que_se_desee  ```
  + *Por ejemplo*  ``` http://localhost:35000/html/movies.html  ```
  
![image]()

# Ejecución de Pruebas 🧪
### Desde el IDE : 
- Para correr la pruebas nos dirigimos al IDE y localizamos una carpeta llamada **test**
  
![image]()
- Del mismo modo que ejecutamos el servidor lo haremos con la clase de pruebas:
  
![image]()
- Resultado✅
  
![image]()

### Por consola seria de la siguiente forma:
- Cambiar directorio
  ```
  cd AREP_LAB02
  ```

- Ejecutar 
  ```
  mvn test
  ```
# Arquitectura 📄 
La arquitectura de la aplicación es una servidor web HTTP sencillo que puede manejar múltiples solicitudes seguidas (no concurrentes). La aplicación está desarrollada en Java y utiliza las librerías de red para manejar las conexiones y solicitudes HTTP.

El servidor web está implementado en la clase HttpServer, que crea un ServerSocket que escucha en el puerto 35000. Cuando un cliente se conecta, el servidor lee la solicitud HTTP y determina el recurso solicitado. Si el recurso es una página HTML, CSS, JavaScript o una imagen, el servidor lee el archivo desde el disco y lo envía como respuesta HTTP con el tipo de contenido apropiado. Si el recurso es una solicitud GET a la ruta "/movie", el servidor envía una respuesta HTTP 200 con el cuerpo de la respuesta obtenido de una API REST en el backend.

El proyecto también incluye una aplicación web simple con una página HTML, un archivo CSS y un archivo JavaScript. La aplicación web realiza una solicitud asíncrona a la API REST en el backend utilizando AJAX.

En resumen, la arquitectura de la aplicación es una servidor web HTTP simple que maneja solicitudes HTTP y envía respuestas HTTP con los archivos solicitados o los datos obtenidos de una API REST en el backend. La aplicación web se comunica con el servidor web utilizando HTTP y AJAX.

# Pruebas 
#### - Al cargar un archivo html:
- http://localhost:35000/html/movies.html
![]()
#### - Al cargar un imagen:
-http://localhost:35000/imagenes/robot.jpg
![]()
#### - Al cargar un archivo css:
- http://localhost:35000/css/movies.css
![]()
#### - Al cargar un archivo js:
- http://localhost:35000/js/ApiConnection.js
![]()

# Autor 
Santiago Naranjo Melo [SantiagoMelo0104](https://github.com/SantiagoMelo0104)
