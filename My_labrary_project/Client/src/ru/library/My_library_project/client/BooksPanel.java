package ru.library.My_library_project.client;

import ru.library.My_library_project.api.data.Book;
import com.caucho.hessian.client.HessianProxyFactory;
import ru.library.My_library_project.api.services.BookService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;

public class BooksPanel extends JPanel {
    public BooksPanel() {
        BookList list = new BookList();
        JTextField bookField = new JTextField(40);
        JTextArea aboutArea = new JTextArea();
        JButton addButton = new JButton("Add");
        JButton delButton = new JButton("Delete");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = JOptionPane.showInputDialog(BooksPanel.this, "Give name of the book","Input", JOptionPane.INFORMATION_MESSAGE);
                String t = JOptionPane.showInputDialog(BooksPanel.this, "Give author of the book","Input", JOptionPane.INFORMATION_MESSAGE);
                if((s != null) && (t !=null)) {
                    Book book = new Book();
                    book.setName(s);
                    book.setAuthor(t);

                    try {
                        String url = "http://127.0.0.1:8080/book";
                        HessianProxyFactory factory = new HessianProxyFactory();
                        BookService bookService = (BookService) factory.create(BookService.class, url);
                        String id =  bookService.addBook(book);
                       // Book book1 = bookService.getBook(id);
 //todo перечитать с сервера
                        list.getBookModel().addBook(book);
                    } catch (MalformedURLException ex) {
                        throw new RuntimeException(ex);
                    }

                }
            }
        });

       delButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Book book = list.getSelectedValue();
                try {
                    String url = "http://127.0.0.1:8080/product";
                    HessianProxyFactory factory = new HessianProxyFactory();
                    BookService productService = (BookService) factory.create(BookService.class, url);
                    productService.delBook(book.getId());
                    list.getBookModel().delBook(book);
                } catch (MalformedURLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.add(addButton);
        toolBar.add(delButton);

        JPanel bookPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bookPanel.add(new JLabel("Book:"));
        bookPanel.add(bookField);

        JPanel aboutPanel = new JPanel(new BorderLayout());
        aboutPanel.add(aboutArea, BorderLayout.CENTER);
        aboutPanel.setBorder(BorderFactory.createTitledBorder("About"));

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(toolBar, BorderLayout.NORTH);
        leftPanel.add(list, BorderLayout.CENTER);


        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(bookPanel, BorderLayout.NORTH);
        rightPanel.add(aboutPanel, BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(300);

        setLayout(new BorderLayout());
        add(splitPane, BorderLayout.CENTER);
    }

}
