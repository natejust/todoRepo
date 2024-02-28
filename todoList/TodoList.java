package todoList;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class TodoList extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private DefaultListModel<String> listModel;
    private JList<String> todoList;
    private JTextField textField;
    

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TodoList frame = new TodoList();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public TodoList() {
		listModel = new DefaultListModel<>();
	    todoList = new JList<>(listModel);
	    textField = new JTextField();
		textField.setBounds(10, 211, 315, 23);
	   
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 String newItem = textField.getText();
		            if (!newItem.isEmpty()) {
		                listModel.addElement(newItem);
		                textField.setText("");
		            }
			}
		});
		btnAdd.setBounds(335, 211, 89, 23);
		contentPane.add(btnAdd);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] selectedIndices = todoList.getSelectedIndices();
	            for (int i = selectedIndices.length - 1; i >= 0; i--) {
	                listModel.remove(selectedIndices[i]);
	            }
			}
		});
		btnDelete.setBounds(335, 79, 89, 23);
		contentPane.add(btnDelete);
		
		JButton btnNewButton = new JButton("Save");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
		            JFileChooser fileChooser = new JFileChooser();
		            int returnValue = fileChooser.showSaveDialog(TodoList.this);
		            if (returnValue == JFileChooser.APPROVE_OPTION) {
		                try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileChooser.getSelectedFile()))) {
		                    for (int i = 0; i < listModel.size(); i++) {
		                        writer.write(listModel.getElementAt(i));
		                        writer.newLine();
		                    }
		                } catch (IOException ex) {
		                    ex.printStackTrace();
		                }
		            }
		        }
		});
		btnNewButton.setBounds(335, 45, 89, 23);
		contentPane.add(btnNewButton);
		
		JButton btnOpen = new JButton("Open");
		btnOpen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
	            int returnValue = fileChooser.showOpenDialog(TodoList.this);
	            if (returnValue == JFileChooser.APPROVE_OPTION) {
	                listModel.clear();
	                try (BufferedReader reader = new BufferedReader(new FileReader(fileChooser.getSelectedFile()))) {
	                    String line;
	                    while ((line = reader.readLine()) != null) {
	                        listModel.addElement(line);
	                    }
	                } catch (IOException ex) {
	                    ex.printStackTrace();
	                }
	            }
			
			}
		});
		btnOpen.setBounds(335, 11, 89, 23);
		contentPane.add(btnOpen);
		
		 JScrollPane scrollPane = new JScrollPane(todoList);
		 scrollPane.setBounds(10, 10, 315, 190);
		 contentPane.add(scrollPane);
		
		
		contentPane.add(textField);
		textField.setColumns(10);
	}
}
