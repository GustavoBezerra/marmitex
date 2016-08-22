package com.les.marmitex.view.command.impl;

import com.les.marmitex.core.fachada.IFachada;
import com.les.marmitex.core.fachada.impl.Fachada;
import com.les.marmitex.view.command.ICommand;


public abstract class AbstractCommand implements ICommand {

	protected IFachada fachada = new Fachada();

}
