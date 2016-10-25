package com.example.Filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gundave on 8/2/2016.
 * This class intercepts requests and responses based on the filter type (filterType) we use and
 * under what conditions this filter should be triggered (shouldFilter).
 * The order in which this filter should be executed (filterOrder)
 * contains functionality of the filter (run)
 * Zuul has four standard filter types:
        pre - filters are executed before the request is routed
        routing - filters can handle the actual routing of the request
        post - filters are executed after the request has been routed
        error - filters execute if an error occurs in the course of handling the request.
 *
 */
public class LoggingFilter extends ZuulFilter {

    private static Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest httpServletRequest = requestContext.getRequest();
        logger.info(String.format("%s request to %s", httpServletRequest.getMethod(), httpServletRequest.getRequestURL().toString()));

        return null;
    }


}
