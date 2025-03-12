# DEVOPS/CI/CD

## Integrantes
Natalia Espitia Espinel

Jesús Alberto Jauregui Conde

Santiago Hurtado Martínez

Andrés Felipe Calderón Ramírez

## Creando los Pipelines (CI - Continous Integration)

Usando el mismo código del proyecto realizado en el laboratorio 4 (generar un repositorio nuevo), para hacer lo siguiente:

### 1. Subir el nuevo repositorio a GitHub

![](/assets/1.png)

### 2. Configurar en github actions un workflow que contendrá 3 jobs

El primer job se llamará build, el segundo test y el tercero deploy, además, este workflow se disparará (events/trigger) on: pull_request, para esto puede usar como base este tutorial o cualquier otro qué considere. Deberás hacer steps o actions sobre cada job para que se pueda obtener los siguiente:

- build: únicamente realizar hasta la fase compile de maven, es prerrequisito de test.
![](/assets/2.png)

- test: realizar la fase de verify y responder ¿se puede lograr que se ejecute sin necesidad de compilar el proyecto?, es dependiente (needs) de build para ser ejecutada, y a su vez es prerrequisito para hacer deploy.
![](/assets/3.png)

- deploy: por ahora deberá imprimir en consola "En construcción ...", necesita (needs) que se haya ejecutado test antes de iniciar.
![](/assets/4.png)
![](/assets/5.png)

### 3. Agregar los siguientes tests
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

### 4. Verifica que la ejecución del workflow es exitosa, si no lo fuera, modifícalo hasta que ocurra
![](/assets/7.png)

## Desplegando en Azure usando CI/CD (Continous Deployment / Continous Delivery)

### 5. En Azure crea un servicio de App Service con recursos que facturen 0 dólares
![](/assets/6.png)

### 6. Configura el job deploy que creaste en el paso 2 y usando el action azure/webapps-deploy@v2 despliega el jar generado a tu servicio de App Service
![](/assets/13.png)

### 7. Verifica qué el endpoint de la aplicación generado en App Service
![](/assets/14.png)

### 8. En este punto la aplicación no debería funcionar, ¿Donde puedes ver el mensaje de error de la aplicación o logs?, (probáblemente está fallando debido a que el puerto usado para despliegue no es el esperado (puerto 80), modifícalo en el application.properties)
![](/assets/15.png)

### 9. En este punto la aplicación no debería funcionar totalmente debido a qué no hay una base de datos preparada, debes verificar esta situación a través de los logs, crea una base de datos MySQL con facturación de 0 dólares


### 10. Para utilizar la base de datos, configura los datos de conexión como una o varias variables de entorno tanto en App Service como en el archivo application.properties de tu proyecto


### 11. Prueba nuevamente la aplicación, ya debería estar funcionando!
