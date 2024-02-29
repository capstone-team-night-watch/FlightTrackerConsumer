package com.capstone.consumer.config;

import com.capstone.consumer.ApplicationProperties;
import com.corundumstudio.socketio.SocketIOServer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ConfigurableApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SocketIo {
    @Mock
    private ApplicationProperties applicationProperties;

    @Mock
    private ConfigurableApplicationContext applicationContext;

    @Mock
    private ApplicationReadyEvent applicationReadyEvent;


    @Mock
    private SocketIOServer mockSocketIoServer;

    @Test
    public void CreateSocketIo_ShouldReadPortFromConfiguration() {
        var socketIoConfig = new SocketIoConfig();

        when(applicationProperties.getSocketIoPort()).thenReturn(8080);
        var socket = socketIoConfig.getSocketIOServer(applicationProperties);

        assertEquals(8080, socket.getConfiguration().getPort());
    }


    @Test
    public void StartSocketIod_ShouldCallSocketStart() {
        var socketIoConfig = new SocketIoConfig();

        when(applicationReadyEvent.getApplicationContext()).thenReturn(applicationContext);
        when(applicationContext.getBean(SocketIOServer.class)).thenReturn(mockSocketIoServer);

        socketIoConfig.startSocketIoServer(applicationReadyEvent);

        verify(mockSocketIoServer, times(1)).start();
    }
}
