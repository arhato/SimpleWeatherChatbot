package trailblazerschatbot.chatbot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

 public class ChatBotGUI extends JFrame {
	 
		    private JPanel chatPanel;
		    private JTextField userInputField;
		    private JTextArea chatbotMessages;
		    private JButton sendButton;
		    

		    public ChatBotGUI() {
		        // Set up the chat panel
		        chatPanel = new JPanel();
		        chatPanel.setLayout(new BorderLayout());
		        chatbotMessages = new JTextArea();
		        chatbotMessages.setEditable(false);
		        JScrollPane scrollPane = new JScrollPane(chatbotMessages);
		        userInputField = new JTextField();
		        sendButton = new JButton("Send");
		        chatPanel.add(scrollPane, BorderLayout.CENTER);
		        JPanel inputPanel = new JPanel(new BorderLayout());
		        inputPanel.add(userInputField, BorderLayout.CENTER);
		        inputPanel.add(sendButton, BorderLayout.EAST);
		        chatPanel.add(inputPanel, BorderLayout.SOUTH);
		        

		        // Set up the main window
		        setTitle("ChatBot");
		        setSize(500, 500);
		        setDefaultCloseOperation(EXIT_ON_CLOSE);
		        add(chatPanel);

		        // Add action listener to the send button
		        sendButton.addActionListener(new ActionListener() {
		            @Override
		            public void actionPerformed(ActionEvent e) {
		            	userInput = userInputField.getText();
		                // Call the chatbot's method to generate a response
		                String chatbotResponse = ChatBotGUITest.generateResponse(userInput);
		                // Append the chatbot's response to the chat panel
		                chatbotMessages.append("Human : " + userInput + "\n");
		                chatbotMessages.append("ChatBot: " + chatbotResponse + "\n");
		                // Clear the user input field
		                userInputField.setText("");
		            }
		        });
		    }
		    
}


