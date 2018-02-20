package nerdeando.config;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.netflix.config.*;
import com.netflix.config.sources.DynamoDbConfigurationSource;
import com.netflix.config.validation.ValidationException;

public class ConfigValuesSingleton implements Runnable{
    // Singleton
    private static ConfigValuesSingleton instance;

    // Properties
    private int amigoJoaoFalou;

    // Flow variables
    private DynamicIntProperty joaoFalou;
    private AmazonDynamoDB dynamo = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.AP_SOUTHEAST_2.getName()).build();
    private DynamoDbConfigurationSource source = new DynamoDbConfigurationSource(dynamo);
    private FixedDelayPollingScheduler scheduler = new FixedDelayPollingScheduler(0, 5000, false);

    private ConfigValuesSingleton() {
        ConfigurationManager.loadPropertiesFromConfiguration(new DynamicConfiguration(source, scheduler));

        this.joaoFalou = DynamicPropertyFactory.getInstance().getIntProperty("amigo.joao.falou", -1, this);
        this.joaoFalou.addValidator(newValue -> {
            if (Integer.parseInt(newValue) < 0) {
                throw new ValidationException("Broooooh, não pode mano: " + newValue);
            }
        });
        this.amigoJoaoFalou = joaoFalou.get();
    }

    public static ConfigValuesSingleton getInstance() {
        if(instance == null)
            instance = new ConfigValuesSingleton();
        return instance;
    }

    public int getAmigoJoaoFalou() {
        return amigoJoaoFalou;
    }

    @Override
    public void run() {
        System.out.println("Mudei mano");
        this.amigoJoaoFalou = joaoFalou.get();
    }
}
