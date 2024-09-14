package com.example.starter.base;

import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import org.vaadin.crudui.crud.impl.GridCrud;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import io.quarkus.runtime.StartupEvent;

/**
 * The main view contains a button and a click listener.
 */
@Route("")
public class MainView extends VerticalLayout {

    @Inject
    GreetService greetService;

    @Transactional
    public void init(@Observes StartupEvent e) {
        new Pizza("Mussarella").persist();
        new Pizza("4 Queijos").persist();
        new Pizza("Calabresa").persist();
    }

    public MainView() {
        TextField textField = new TextField("Your Name");
        textField.addThemeName("bordered");

        Button button = new Button("Say hello", e -> {
            add(new Paragraph(greetService.greet(textField.getValue())));
        });

        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        button.addClickShortcut(Key.ENTER);

        // addClassName("centered-content");

        GridCrud<Pizza> crud = new GridCrud<>(Pizza.class);
        crud.setFindAllOperation(() -> Pizza.listAll());
        add(textField, button, crud);

    }
}
