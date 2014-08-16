LATEX = pdflatex
# LATEX = latex 
VIEWER = xpdf
# VIEWER = xdvi

CURRTARGET = pa2template
#OUTDIR = ./output

default: f

f:
	make -s tex-full; make -s view&

tex-full:
	$(LATEX) $(CURRTARGET).tex; \
	bibtex $(CURRTARGET); \
	$(LATEX) $(CURRTARGET).tex; \
	$(LATEX) $(CURRTARGET).tex; \
	#for i in dvi log aux blg bbl; \
	#	do cp $(CURRTARGET).$$i /tmp/; mv $(CURRTARGET).$$i $(OUTDIR); done \
	#dvipdf $(OUTDIR)/$(CURRTARGET).dvi ; mv $(CURRTARGET).pdf $(OUTDIR) & 

		
view:
	$(VIEWER) $(CURRTARGET).pdf &

ps:
	dvips -o $(OUTDIR)/$(CURRTARGET).ps $(OUTDIR)/$(CURRTARGET).dvi &

pdf:
	dvipdf $(OUTDIR)/$(CURRTARGET).dvi ; mv $(CURRTARGET).pdf $(OUTDIR) & 


clean:
	rm *.log *.blg *.dvi *.aux *.ppl *~ $(OUTDIR)/*.log $(OUTDIR)/*.bib $(OUTDIR)/*.aux $(OUTDIR)/*.bbl $(OUTDIR)/*.blg $(OUTDIR)/*.dvi ; mv *.ps *.pdf $(OUTDIR)
