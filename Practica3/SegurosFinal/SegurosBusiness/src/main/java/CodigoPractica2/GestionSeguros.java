package CodigoPractica2;



public class GestionSeguros implements IGestionClientes, IGestionSeguros, IInfoSeguros{
	
	private IClientesDAO daoClientes;
    private ISegurosDAO daoSeguros;

	public GestionSeguros(IClientesDAO daoClientes, ISegurosDAO daoSeguros) {
	
		
	}
	
	
public Cliente nuevoCliente (Cliente c) throws DataAccessException {
	
	return daoClientes.creaCliente(c);
	

	
}

public Cliente bajaCliente (String dni) throws OperacionNoValida, DataAccessException {
	
	
	Cliente c = daoClientes.cliente(dni);
    if (c == null) return null;

 
    if (c.getSeguros() != null && !c.getSeguros().isEmpty()) {
        throw new OperacionNoValida("No se puede eliminar: el cliente tiene seguros asociados.");
    }

    return daoClientes.eliminaCliente(dni);
}


public Seguro nuevoSeguro (Seguro s, String dni) throws DataAccessException {
	
	

	Cliente c = daoClientes.cliente(dni);
    if (c == null) return null;

    
    if (daoSeguros.seguroPorMatricula(s.getMatricula()) != null) {
        throw new OperacionNoValida("El seguro con esta matrícula ya existe.");
    }


    return daoSeguros.creaSeguro(s);
}

public Seguro bajaSeguro (String matricula, String dni) throws OperacionNoValida, DataAccessException{
	
	Cliente c = daoClientes.cliente(dni);
    Seguro s = daoSeguros.seguroPorMatricula(matricula);

    if (c == null || s == null) { 
    	
    	return null;
    }

    if (!c.getSeguros().contains(s)) {
        throw new OperacionNoValida("El seguro no pertenece al DNI indicado.");
    }

    return daoSeguros.eliminaSeguro(s.getId());
}

public Seguro anhadeConductorAdicional (String matricula, String conductor) throws DataAccessException {
	
	Seguro s = daoSeguros.seguroPorMatricula(matricula);
	
    if (s == null) {
    
    return null;
    
    }

    s.setConductorAdicional(conductor);
    return daoSeguros.actualizaSeguro(s);
}

public Cliente cliente (String dni) throws DataAccessException{

	return daoClientes.cliente(dni);
}

public Seguro seguro (String matricula) throws DataAccessException {
	
	return daoSeguros.seguroPorMatricula(matricula);
}

	

}
