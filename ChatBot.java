package firstpackage;


public class ChatBot {
    public static String generateResponse(String userInput) {
        String response = "";
        // Logic to generate response based on user input
        if (userInput.toLowerCase().contains("hello") || userInput.toLowerCase().contains("hi")) {
            response = "Hi There!";
        } else if (userInput.toLowerCase().contains("how are you")) {
            response = "I'm doing well, thanks for asking.";
        } else if (userInput.toLowerCase().contains("what is your name")) {
            response = "My name is ChatBot.";
        }else if (userInput.toLowerCase().contains("haal")) {
        	response = "Very good Very good";
        }
        else {
            response = "I'm sorry, I don't understand. Can you please rephrase?";
        }
        return response;
    }
}
