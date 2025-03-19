# CI/CD

## Integrantes
Natalia Espitia Espinel

Jesús Alberto Jauregui Conde

Santiago Hurtado Martínez

Andrés Felipe Calderón Ramírez

## PARTE I. DEVOPS / CI-CD

### Creando los Pipelines (CI - Continous Integration)

Usando el mismo código del proyecto realizado en el laboratorio 4 (generar un repositorio nuevo), para hacer lo siguiente:

#### 1. Subir el nuevo repositorio a GitHub

![](/assets/1.png)

#### 2. Configurar en github actions un workflow que contendrá 3 jobs

El primer job se llamará build, el segundo test y el tercero deploy, además, este workflow se disparará (events/trigger) on: pull_request, para esto puede usar como base este tutorial o cualquier otro qué considere. Deberás hacer steps o actions sobre cada job para que se pueda obtener los siguiente:

- build: únicamente realizar hasta la fase compile de maven, es prerrequisito de test.

![](/assets/2.png)

- test: realizar la fase de verify y responder ¿se puede lograr que se ejecute sin necesidad de compilar el proyecto?, es dependiente (needs) de build para ser ejecutada, y a su vez es prerrequisito para hacer deploy.

![](/assets/3.png)

- deploy: por ahora deberá imprimir en consola "En construcción ...", necesita (needs) que se haya ejecutado test antes de iniciar.

![](/assets/4.png)
![](/assets/5.png)

#### 3. Agregar los siguientes tests
- Dado que tengo 1 reserva registrada, Cuando lo consulto a nivel de servicio, Entonces la consulta será exitosa validando el campo id.

![](/assets/8.png)

- Dado que no hay ninguna reserva registrada, Cuándo la consulto a nivel de servicio, Entonces la consulta no retornará ningún resultado.

![](/assets/9.png)

- Dado que no hay ninguna reserva registrada, Cuándo lo creo a nivel de servicio, Entonces la creación será exitosa.

![](/assets/10.png)

- Dado que tengo 1 reserva registrada, Cuándo la elimino a nivel de servicio, Entonces la eliminación será exitosa.

![](/assets/11.png)

- Dado que tengo 1 reserva registrada, Cuándo la elimino y consulto a nivel de servicio, Entonces el resultado de la consulta no retornará ningún resultado.

![](/assets/12.png)

#### 4. Verifica que la ejecución del workflow es exitosa, si no lo fuera, modifícalo hasta que ocurra

![](/assets/7.png)

### Desplegando en Azure usando CI/CD (Continous Deployment / Continous Delivery)

#### 5. En Azure crea un servicio de App Service con recursos que facturen 0 dólares

![](/assets/6.png)

#### 6. Configura el job deploy que creaste en el paso 2 y usando el action azure/webapps-deploy@v2 despliega el jar generado a tu servicio de App Service

![](/assets/13.png)

#### 7. Verifica qué el endpoint de la aplicación generado en App Service

![](/assets/14.png)

#### 8. En este punto la aplicación no debería funcionar, ¿Donde puedes ver el mensaje de error de la aplicación o logs?, (probáblemente está fallando debido a que el puerto usado para despliegue no es el esperado (puerto 80), modifícalo en el application.properties)

![](/assets/15.png)

#### 9. En este punto la aplicación no debería funcionar totalmente debido a qué no hay una base de datos preparada, debes verificar esta situación a través de los logs, crea una base de datos MySQL con facturación de 0 dólares
Este punto no se pudieron realizar con MySQL sino que se realizaron con conexión con MongoDB Atlas


#### 10. Para utilizar la base de datos, configura los datos de conexión como una o varias variables de entorno tanto en App Service como en el archivo application.properties de tu proyecto
Este punto no se pudieron realizar con MySQL sino que se realizaron con conexión con MongoDB Atlas

#### 11. Prueba nuevamente la aplicación, ya debería estar funcionando!
Funcionamiento de la aplicación con MongoDB Atlas y Postman para ingresar los datos

![](/assets/16.png)

![](/assets/19.png)

![](/assets/17.png)

![](/assets/18.png)

![](/assets/20.png)

## PARTE II. GRÁFICOS

### Generación de datos por procedimientos

Sobre el mismo proyecto:

#### 1. Agrega un nuevo campo, prioridad de la reserva(de 1 a 5) donde 1 es baja prioridad y 5 alta.

Modelo
![](/assets/21.png)
![](/assets/22.png)

#### 2. Genera "proceduralmente" nuevas reservas, aleatoriamente serán entre 100 y 1000 (Corresponden a las reservas de los laboratorios). No se necesita crear ningún tipo de interfaz gráfica para poder llenarla, esta parte de la lógica pertenece al dominio de la capa de servicio.

Servicio
![](/assets/23.png)

Controlador
![](/assets/24.png)

#### 3. Escoje entre algunas de las bibliotecas de gráficos en javascript y sustente su decisión, algunas que podría considerar son: d3.js, c3.js, chart.js, Google Charts, entre otras, para sustentar apoyese de instrumentos como las tabla T "pros y cons".

![](/assets/25.png)

Elección Chart.js
- Facilidad de Uso: Requiere poca configuración y tiene una API intuitiva.
- Buen Equilibrio entre Flexibilidad y Simplicidad: Permite personalización sin la complejidad de D3.js.
- Rendimiento Adecuado: Aunque no es el más rápido, maneja bien conjuntos de datos medianos.
- Compatibilidad y Soporte: Funciona sin necesidad de conexión externa y tiene una comunidad activa.

#### 4. Realiza una página nueva en la estructura, esta página será la de "analítica" o "insights", en está página se podrá seleccionar entre las siguientes gráficas

- Histograma de reservas por promedio de duración en laboratorios.
Un gráfico de barras que muestra el promedio de la ducarción de las reservas por laboratorio. Es útil para ver la distribución de duración de las reservas entre los laboratorios.

![](/assets/26.png)

- Reservas por Laboratorio
Otro gráfico de barras que muestra el número de reservas por laboratorio. Similar al histograma, pero puede ser más específico en términos de tiempo o estado.

![](/assets/27.png)

- Promedios de reservas por prioridad.
Un gráfico de líneas que muestra el promedio de reservas por nivel de prioridad. Esto ayuda a entender cómo se distribuyen las reservas en términos de urgencia.

![](/assets/28.png)

- Laboratorios por nivel de reserva para comprender cuales tienen más demandas.
Un gráfico de pie que muestra la demanda relativa de cada laboratorio. Es útil para identificar qué laboratorios son más populares.

![](/assets/29.png)