# EXAMEN SUPLETORIO

## Desarrollo de una Aplicación Android implementando funcionalidades de Google Places API.

En esta app se utilizó la API de GooglePlaces, para el consumo de esta se utilizó Volley. Y para el cargado de las imagenes se utilizó Piccaso (para el infoWindow) 
y Glide (para la personalización del marcador de GoogleMaps).

### Flujo de trabajo de la aplicación.
A continuación se puede observar la interfaz principal de la aplicación. La cual tiene un Spinner, un Mapa de Google y un boton.
![alt text](https://github.com/AndyNinasunta/ExamenNearbySearchfeature/blob/master/1.PNG)

Si el usuario da clic en el boton Buscar, sin haber seleccionado un filtro en el Spinner. Se cargarán todos los tipos de establecimientos que se encuentren en un radio de 100 metros.

![alt text](https://github.com/AndyNinasunta/ExamenNearbySearchfeature/blob/master/2.PNG)
![alt text](https://github.com/AndyNinasunta/ExamenNearbySearchfeature/blob/master/3.PNG)

Si selecciona un filtro del Spinner, se cargarán los establecimientos de ese determinado tipo seleccionado.
En la siguiente captura puede encontrar un ejemplo con los restaurantes.

![alt text](https://github.com/AndyNinasunta/ExamenNearbySearchfeature/blob/master/4.PNG)
![alt text](https://github.com/AndyNinasunta/ExamenNearbySearchfeature/blob/master/5.PNG)

Aca encuentra otro ejemplo, pero en este caso con un GYM.

![alt text](https://github.com/AndyNinasunta/ExamenNearbySearchfeature/blob/master/6.PNG)
