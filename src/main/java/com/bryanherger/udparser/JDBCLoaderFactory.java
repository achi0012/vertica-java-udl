package com.bryanherger.udparser;

import com.vertica.sdk.*;

public class JDBCLoaderFactory extends ParserFactory {
    @Override
    public void plan(ServerInterface srvInterface,
                     PerColumnParamReader perColumnParamReader,
                     PlanContext planCtxt) throws UdfException {
        if (!srvInterface.getParamReader().containsParameter("connect")) {
            throw new UdfException(0, "Error:  JDBCLoader requires a 'connect' string containing JDBC connection URL");
        }
        if (!srvInterface.getParamReader().containsParameter("query")) {
            throw new UdfException(0, "Error:  JDBCLoader requires a 'query' string, the query to execute on the remote system");
        }
    }

    @Override
    public UDParser prepare(ServerInterface srvInterface,
                            PerColumnParamReader perColumnParamReader,
                            PlanContext planCtxt,
                            SizedColumnTypes returnType) {
        return new JDBCLoader();
    }

    @Override
    public void getParameterType(ServerInterface srvInterface,
                                 SizedColumnTypes parameterTypes) {
        parameterTypes.addVarchar(65000, "connect");
        parameterTypes.addVarchar(65000, "query");
    }
}
