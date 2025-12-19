# Online Store REST API

## Project Overview

This project is a **REST API for an Easyshop online store**. The goal was to practice what we learned in class by building something that actually works, breaks, and then gets fixed (kind of like me before coffee).

The API lets you manage categories and products, log in users, store shopping carts, and update user profiles. Everything is based on what we learned in the workbook nothing fancy.

Things this project focuses on:

* Spring Boot controllers
* JDBC DAOs with MySQL
* Dependency Injection
* Basic role-based security (USER vs ADMIN)

---

## Project Phases & What I Built

### Phase 1 – Categories Controller

I implemented the `CategoriesController` so the API can manage product categories. Only users with the **ADMIN** role can add, update, or delete categories (because giving everyone admin access is how you end up on the news).

**Endpoints**

* `GET /categories`
* `GET /categories/{id}`
* `POST /categories` *(ADMIN only)*
* `PUT /categories/{id}` *(ADMIN only)*
* `DELETE /categories/{id}` *(ADMIN only)*

**Category JSON Example**

```json
{
  "categoryId": 1,
  "name": "Electronics",
  "description": "Explore the latest gadgets and devices"
}
```

All database logic for categories lives in `MySqlCategoryDao`, which uses JDBC and prepared statements.

---

### Phase 2 – Bug Fixes (Products)

#### Bug 1 – Product Search Issues

The product search endpoint was returning incorrect results, so I tested different combinations of query parameters and fixed the logic so the filters work correctly.

You can now search by:

* category
* price range
* subcategory

**Example URLs**

```
/products?cat=1
/products?cat=1&subCategory=red
/products?minPrice=25
/products?minPrice=25&maxPrice=100
```

#### Bug 2 – Duplicate Products

This one was painful. Updating a product was accidentally creating new rows instead of updating the existing one. That’s how we ended up with the same laptop showing up three times.

This was fixed so:

* `PUT /products/{id}` updates the correct product
* No duplicate rows are created

Only **ADMIN** users are allowed to create, update, or delete products.

---

### Phase 3 – Shopping Cart (Optional Feature)

I implemented a shopping cart that works for logged-in users. If a user logs out and comes back later, their cart is still there.

**Endpoints**

* `GET /cart`
* `POST /cart/products/{productId}`
* `PUT /cart/products/{productId}` *(bonus)*
* `DELETE /cart`

**Shopping Cart JSON Example**

```json
{
  "items": {
    "1": {
      "product": { "productId": 1, "name": "Smartphone", "price": 499.99 },
      "quantity": 2,
      "lineTotal": 999.98
    }
  },
  "total": 999.98
}
```

What the cart does:

* Adds products for the current user
* Increases quantity if the product is already in the cart
* Clears the cart when requested

---

### Phase 4 – User Profile (Optional Feature)

Users can view and update their profile information.

**Endpoints**

* `GET /profile`
* `PUT /profile`

Profiles are tied to users by `userId` and handled through `ProfileDao` and `MySqlProfileDao`.

---

## Authentication & Security

* Starter code was provided - thanks Dave!
* Anyone can browse products and categories
* You must be logged in to use the cart or profile
* Admin-only actions are protected with `@PreAuthorize`


---

## Technology Used

* Java
* Spring Boot
* Spring Security
* JDBC
* MySQL
* Maven
* Insomnia for testing

---
## Testing in Insomnia

![Screenshot 2025-12-19 092635.png](../backend-api/src/main/resources/Screenshot%202025-12-19%20092635.png)
![Screenshot 2025-12-19 092614.png](../backend-api/src/main/resources/Screenshot%202025-12-19%20092614.png)

## Running the Application

1. Create the MySQL database and tables
2. Update `application.properties` with your database credentials
3. Run the project in IntelliJ
4. Test the API using Insomnia

---

## Author

Mercy Evans
And as ever, thank you to all my peers who helped me debug!