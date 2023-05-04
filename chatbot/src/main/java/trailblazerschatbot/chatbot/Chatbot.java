package trailblazerschatbot.chatbot;

import java.awt.BorderLayout;
import java.io.File;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.History;
import org.alicebot.ab.MagicBooleans;
import org.alicebot.ab.MagicStrings;
import org.alicebot.ab.utils.IOUtils;

public class Chatbot extends JFrame {
	private static final boolean TRACE_MODE = false;
	static String botName = "super";
	static UserData data;
	static boolean isInitialized = false;
	static String tempDestination;
	static int duration;
	static String initialDay;

	private JPanel chatPanel;
	private JTextField userInputField;
	private JTextArea chatbotMessages;
	private JButton sendButton;

	public static void main(String[] args) {
		Chatbot chatBot = new Chatbot();
		chatBot.setVisible(true);
	}

	public Chatbot() {
		try {

			String resourcesPath = getResourcesPath();
			System.out.println(resourcesPath);
			MagicBooleans.trace_mode = TRACE_MODE;
			Bot bot = new Bot("super", resourcesPath);
			Chat chatSession = new Chat(bot);
			bot.brain.nodeStats();
			

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
			setTitle("Travel ChatBot");
			setSize(500, 500);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			add(chatPanel);

			// Add action listener to the send button
			sendButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String userInput = userInputField.getText();
					if ((userInput == null) || (userInput.length() < 1))
						userInput = MagicStrings.null_input;
					if (userInput.equals("q")) {
						System.exit(0);
					} else if (userInput.equals("wq")) {
						bot.writeQuit();
						System.exit(0);
					} else {
					String request = userInput;
					if (MagicBooleans.trace_mode)
						System.out.println(
								"STATE=" + request + ":THAT=" + ((History) chatSession.thatHistory.get(0)).get(0)
										+ ":TOPIC=" + chatSession.predicates.get("topic"));
					String response = chatSession.multisentenceRespond(request);

					// User input data
					if ((!(chatSession.predicates.get("noOfDays").equals("unknown"))) && (!isInitialized)) {
						data = new UserData(Integer.parseInt(chatSession.predicates.get("noOfDays")));
						isInitialized = true;
					}
					if ((!(chatSession.predicates.get("destination").equals("unknown"))) && (isInitialized)
							&& (!(chatSession.predicates.get("destination").equals(tempDestination)))) {
						data.increment();
						data.addCity(chatSession.predicates.get("destination"));
						tempDestination = chatSession.predicates.get("destination");
					}
					if ((!(chatSession.predicates.get("day").equals("unknown"))) && (isInitialized)) {
						initialDay = chatSession.predicates.get("day");
					}
					if ((!(chatSession.predicates.get("duration").equals("unknown"))) && (isInitialized)) {
						duration = Integer.parseInt(chatSession.predicates.get("duration"));
					}
					if (duration > 0) {
						data.addDay(initialDay, duration);
						duration = 0;
					}
					if ((!(chatSession.predicates.get("anymore").equals("unknown"))) && (isInitialized)) {
						data.getWeather();
					}
					while (response.contains("&lt;"))
						response = response.replace("&lt;", "<");
					while (response.contains("&gt;"))
						response = response.replace("&gt;", ">");
					chatbotMessages.append("You: " + userInput + "\n");
					chatbotMessages.append("Robot : " + response+ "\n");
					// Clear the user input field
					userInputField.setText("");
				}
			}
			});
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String getResourcesPath() {
		File currDir = new File(".");
		String path = currDir.getAbsolutePath();
		path = path.substring(0, path.length() - 2);
		System.out.println(path);
		String resourcesPath = path + File.separator + "src" + File.separator + "main" + File.separator + "resources";
		return resourcesPath;
	}
}