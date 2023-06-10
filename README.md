# spring-graphql-data-bug
This application demonstrates the Spring GraphQL error.

## Description

The error appears when Connection is used, and Page<?>(spring data) is returned from the @MutationMapping controller.

```
The field at path '/createProduct/edges' was declared as a non null type, but the code
involved in retrieving data has wrongly returned a null value. The graphql specification
requires that the parent field be set to null, or if that is non nullable that it bubble
up null to its parent and so on. The non-nullable type is '[ProductEdge]' within
parent type 'ProductConnection'
```

But if you return query from the controller instead of mutation, then there is no error.


Controller:
```
@Controller
public class GraphQlController {

    private Page<Product> getResult() {
        List<Product> products = List.of(new Product("Demo"));
        return new PageImpl<>(
                products,
                PageRequest.of(0, products.size()),
                products.size()
        );
    }

    @MutationMapping("createProduct")
    public Page<Product> createProduct() {
        return getResult();
    }

    @QueryMapping("getProducts")
    public Page<Product> getProducts() {
        return getResult();
    }

}
```

Schema:
```
type Product {
    title: String
}

type Mutation {
    createProduct : ProductConnection!
}

type Query {
    getProducts : ProductConnection
}
```

Example of a request with an error:
```
mutation {
  createProduct {
    edges {
      node {
        title
      }
    } 
  }
}
```

Query without error:

```
{
  getProducts {
    edges {
      node {
        title
      } 
    } 
  } 
}
```

Both requests return the same data.



### Build:
To build the application, you need to install JDK/OpenJDK at least 17

Build from shell:
```
git clone https://github.com/gnumbix/spring-graphql-data-bug.git
cd spring-graphql-data-bug
```

Windows:

```mvnw.cmd package```

Linux/Mac:

```./mvnw package```

Run: 
```
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

Open in a browser
```
http://127.0.0.1:8080/graphiql
```

The application can also be built in the IDE(intelij.. etc).


## Lib version

```
spring boot 3.1.0
spring-graphql 1.2.0
graphql-java 20.2
JDK 17
```

