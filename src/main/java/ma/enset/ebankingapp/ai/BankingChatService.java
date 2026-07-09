package ma.enset.ebankingapp.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Service;

@Service
public class BankingChatService {

    private final ChatClient chatClient;

    public BankingChatService(ChatModel chatModel, BankingTools bankingTools) {
        this.chatClient = ChatClient.builder(chatModel)
                .defaultSystem("""
                    You are an AI-powered banking assistant for the e-Banking digital platform.
                    You have access to the bank's database through specialized tools.
                    
                    Your capabilities:
                    - List and look up customers by name or ID
                    - View bank account details, balances, and types (Current Account / Saving Account)
                    - Retrieve transaction history (DEBIT and CREDIT operations) for any account
                    
                    Rules:
                    - Always use the available tools to fetch real data before answering
                    - Format monetary amounts in MAD (Moroccan Dirham) with 2 decimal places
                    - Be professional, concise, and helpful
                    - If you cannot find what the user is asking for, say so clearly
                    - Never invent or fabricate banking data
                    """)
                .defaultTools(bankingTools)
                .build();
    }

    public String chat(String userMessage) {
        return chatClient.prompt()
                .user(userMessage)
                .call()
                .content();
    }
}
