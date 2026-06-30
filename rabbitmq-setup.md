# Configuración RabbitMQ

## Servicio Docker

RabbitMQ se levanta mediante docker-compose usando la imagen:

rabbitmq:3-management

## Puertos

- 5672: comunicación AMQP entre microservicios y RabbitMQ.
- 15672: panel web de administración RabbitMQ.

## Credenciales

- Usuario: medical_user
- Password: medical_pass

## Colas implementadas

- medical.alerts.oracle.queue
- medical.alerts.file.queue
- medical.summaries.queue

## Exchanges implementados

- medical.alerts.exchange
- medical.summaries.exchange

## Ejecución

```bash
docker compose up -d --build
```
## Validación

Ingresar a: http://localhost:15672

Luego revisar las colas en la sección "Queues and Streams".

Importa en <i>Postman</i> la colección alojada en la carpeta <code>postman</code> ubicada en la raíz del proyecto, la cual contiene todos los endpoints a testear.