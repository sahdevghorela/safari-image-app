package commands

import org.crsh.cli.Command
import org.crsh.cli.Man
import org.crsh.cli.Usage
import org.crsh.command.InvocationContext
import org.springframework.beans.factory.ListableBeanFactory
import org.springframework.boot.actuate.endpoint.MetricsEndpoint
import org.springframework.boot.actuate.health.HealthIndicator

@Usage("various commands to interact with spring boot image app")
class springagram {

    @Usage("Prints out the metrics for spring boot image app")
    @Man("Iterates over all metrics")
    @Command
    void metrics(InvocationContext context){
        ListableBeanFactory beanFactory = context.attributes['spring.beanfactory']
        beanFactory.getBeansOfType(MetricsEndpoint).each {name,metrics ->
            metrics.invoke().each {k,v ->
                out.println "${k} is at ${v}"
            }
        }
    }

    @Usage("Shows the health of app")
    @Man("exercise the health check")
    @Command
    void health(InvocationContext context){
        ListableBeanFactory beanFactory = context.attributes['spring.beanfactory']
        beanFactory.getBeansOfType(HealthIndicator).each {name,indicator ->
            def health = indicator.health()
            out.println "${name} is ${health.status} (${health.details})"
        }
    }
}
