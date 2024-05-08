# Documentación para la Librería accountIPC.jar:

## Clases del Modelo:

1. **User:**
   - Atributos:
     - name (String): Nombre del usuario.
     - surname (String): Apellido del usuario.
     - email (String): Correo electrónico del usuario.
     - nickName (String): Nombre de usuario único utilizado para acceder al sistema.
     - password (String): Contraseña del usuario.
     - registerDate (LocalDate): Fecha de registro del usuario en la aplicación.
     - image (Image): Imagen de perfil del usuario (opcional).
   - Métodos:
     - `User(String name, String surname, String email, String nickName, String password, LocalDate registerDate, Image image)`: Constructor para crear un nuevo objeto User.
     - Getters y setters para todos los atributos, excepto nickName.

2. **Category:**
   - Atributos:
     - name (String): Nombre de la categoría de gasto.
     - description (String): Descripción de la categoría.
   - Métodos:
     - `Category(String name, String description)`: Constructor para crear un nuevo objeto Category.
     - Getters y setters para ambos atributos.

3. **Charge:**
   - Atributos:
     - Id (int): Identificador único del gasto en la base de datos.
     - name (String): Nombre o título del gasto.
     - description (String): Descripción del gasto.
     - category (Category): Categoría a la que pertenece el gasto.
     - cost (double): Costo del gasto.
     - units (int): Unidades del gasto.
     - date (LocalDate): Fecha del gasto.
     - scanImage (Image): Imagen escaneada del recibo (opcional).
   - Métodos:
     - `Charge(String name, String description, Category category, double cost, int units, LocalDate date, Image scanImage)`: Constructor para crear un nuevo objeto Charge.
     - Getters y setters para todos los atributos, excepto Id.

4. **Acount:**
   - Métodos:
     - `getInstance()`: Retorna la única instancia de la clase Acount (Singleton).
     - `registerUser(String name, String surname, String email, String login, String password, Image image, LocalDate date)`: Registra un nuevo usuario en el sistema.
     - `logInUserByCredentials(String login, String password)`: Autentica a un usuario utilizando sus credenciales.
     - `logOutUser()`: Cierra la sesión del usuario actual.
     - `registerCategory(String name, String description)`: Registra una nueva categoría de gasto.
     - `removeCategory(Category category)`: Elimina una categoría de gasto.
     - `getUserCategories()`: Obtiene todas las categorías de gastos del usuario.
     - `registerCharge(String name, String description, double cost, int units, Image scanImage, LocalDate date, Category category)`: Registra un nuevo gasto.
     - `removeCharge(Charge charge)`: Elimina un gasto.
     - `getUserCharges()`: Obtiene todos los gastos del usuario.


# Documentación para la Librería SQLite:

La librería SQLite-JDBC-3.41.2.1.jar proporciona funcionalidades para interactuar con bases de datos SQLite desde una aplicación Java. Aquí se presentan los pasos básicos para utilizar esta librería:

**Métodos Adicionales para Manipulación de Datos:**

   - `registerUser(String name, String surname, String email, String login, String password, Image image, LocalDate date)`: Registra un nuevo usuario en el sistema con los datos proporcionados, como nombre, apellido, correo electrónico, nombre de usuario (login), contraseña, imagen de perfil (opcional) y fecha de registro.
   
   - `logInUserByCredentials(String login, String password)`: Autentica a un usuario utilizando su nombre de usuario (login) y contraseña. Si las credenciales son válidas, el usuario queda registrado en la sesión actual.
   
   - `logOutUser()`: Cierra la sesión del usuario actual, lo que impide el acceso a las funcionalidades protegidas de la aplicación hasta que se vuelva a autenticar.
   
   - `registerCategory(String name, String description)`: Registra una nueva categoría de gasto en el sistema para el usuario logueado, proporcionando un nombre y una descripción para la categoría.
   
   - `removeCategory(Category category)`: Elimina una categoría de gasto existente del usuario logueado, junto con todos los gastos asociados a esa categoría.
   
   - `getUserCategories()`: Obtiene todas las categorías de gastos registradas por el usuario logueado en la sesión actual.
   
   - `registerCharge(String name, String description, double cost, int units, Image scanImage, LocalDate date, Category category)`: Registra un nuevo gasto para el usuario logueado, especificando detalles como nombre, descripción, costo, unidades, imagen del recibo (opcional), fecha y categoría del gasto.
   
   - `removeCharge(Charge charge)`: Elimina un gasto específico del usuario logueado en la sesión actual.
   
   - `getUserCharges()`: Obtiene todos los gastos registrados por el usuario logueado en la sesión actual.
