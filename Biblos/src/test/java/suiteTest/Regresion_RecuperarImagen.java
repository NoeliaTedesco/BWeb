package suiteTest;

import java.io.IOException;
import java.lang.reflect.Method;

import org.testng.annotations.Test;

import components.Hook;
import steps.BuscarImagenes_Step;

public class Regresion_RecuperarImagen extends Hook {

	// Suite de buscar imagenes

	@Test
	public void Test_Imagen_01(Method method) throws IOException {
		BuscarImagenes_Step.Run(method.getName());
	}

}
