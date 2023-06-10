package com.example.demo;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.data.domain.Page;

import java.util.List;

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

    /**
     * GraphQL Mutation. Error:
     * The field at path '/createProduct/edges' was declared as a non null type,
     * but the code involved in retrieving data has wrongly returned a null value.
     * The graphql specification requires that the parent field be set to null, or if that is non nullable that it
     * bubble up null to its parent and so on. The non-nullable type is '[ProductEdge]' within
     * parent type 'ProductConnection'
     * @return Product list as edge
     */
    @MutationMapping("createProduct")
    public Page<Product> createProduct() {
        return getResult();
    }

    /**
     * GraphQL Query. Works without errors
     * @return Product list as edge
     */
    @QueryMapping("getProducts")
    public Page<Product> getProducts() {
        return getResult();
    }

}
